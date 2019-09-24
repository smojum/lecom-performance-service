package com.landsend.lecom.lecomperformanceservice;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unbescape.css.CssStringEscapeLevel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
@Slf4j
public class PerformanceMetricsController {
    @Autowired
    private PerformanceMetricsRepository repository;

    @PostMapping("metrics")
    public void addData(@RequestBody PerformanceMetrics performanceMetrics) {
        setBaseUrl(performanceMetrics);
        setUrlType(performanceMetrics);
        repository.save(performanceMetrics);
    }

    private void setBaseUrl(PerformanceMetrics metrics) {
        String urlString = metrics.getUrl();
        try {
            URL url = new URL(urlString);
            metrics.setBaseUrl(url.getHost());
        } catch (MalformedURLException e) {
            //let the Base url be null.
        }
    }

    private void setUrlType(PerformanceMetrics metrics) {
        String url = metrics.getUrl();
        if (url.toLowerCase().contains("/shop/")) {
            metrics.setUrlType("PLP");
        } else if (url.toLowerCase().contains("/products/")) {
            metrics.setUrlType("PDP");
        } else if (url.toLowerCase().contains("/search?")) {
            metrics.setUrlType("Search");
        } else {
            metrics.setUrlType("Other");
        }
    }

    @GetMapping(value = "report/{id}")
    public ResponseEntity<byte[]> getHtmlData(@PathVariable("id") String id) throws IOException {
        Optional<PerformanceMetrics> repositoryResponse = repository.findById(id);
        if (repositoryResponse.isPresent()) {
            byte[] contents = getHtmlFromZip(repositoryResponse.get().getHtml());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_HTML);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
            return response;
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private static byte[] getHtmlFromZip(String base64zip) throws IOException {
        byte[] zip = Base64.decodeBase64(base64zip.getBytes());
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zip));
        ZipEntry zipEntry = zis.getNextEntry();
        return IOUtils.toByteArray(zis);
    }

    public static void main(String args[]) throws IOException {
        String reportFolder = new File("reports").getAbsolutePath();
        String input = new String(Files.readAllBytes(Paths.get(reportFolder + "/input.txt")));;
        byte[] output = getHtmlFromZip(input);
        Path path = Paths.get(Paths.get(reportFolder) + "/testreport.html");
        Files.write(path, output);
    }
}
