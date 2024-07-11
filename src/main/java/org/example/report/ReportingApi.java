package org.example.report;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.ReportEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class ReportingApi {
    public static void report(String url, String ip) {
        try {
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(5000);
            factory.setReadTimeout(5000);
            RestTemplate restTemplate = new RestTemplate(factory);

            RetryTemplate retryTemplate = new RetryTemplate();
            retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3));
            retryTemplate.setBackOffPolicy(new FixedBackOffPolicy());//Retry after 1 second

            ReportEntity body = new ReportEntity(ip);

            // Setting up the headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<ReportEntity> request = new HttpEntity<>(body, headers);
            String response = retryTemplate.execute((RetryCallback<String, Exception>) context -> restTemplate.postForObject(url, request, String.class));
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
