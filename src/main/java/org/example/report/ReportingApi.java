package org.example.report;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.ReportEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class ReportingApi {
    public static void report(String url, String ip) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ReportEntity body = new ReportEntity(ip);

            // Setting up the headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<ReportEntity> request = new HttpEntity<>(body, headers);

            String response = restTemplate.postForObject(url, request, String.class);
            log.info("response: " + response);
        } catch (Exception err) {
            log.error("have error: ", err);
        }
    }

    public static void report(String[] urls, String ip){
        for (String url: urls){
            report(url, ip);
        }
    }

    public static void main(String[] args) {
        String url = "http://localhost:6868/monitor/report";
        String ip = "154.20.200.88";
        report(url,ip);

    }
}
