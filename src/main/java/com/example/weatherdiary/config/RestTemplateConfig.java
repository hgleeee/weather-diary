package com.example.weatherdiary.config;

import com.example.weatherdiary.utils.ConnectionConst;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    RestTemplate restTemplate(HttpComponentsClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    HttpComponentsClientHttpRequestFactory factory(CloseableHttpClient closeableHttpClient) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setHttpClient(closeableHttpClient);
        return factory;
    }

    @Bean
    public CloseableHttpClient closeableHttpClient() {
        return HttpClients.custom()
                .setConnectionManager(poolingHttpClientConnectionManager())
                .build();
    }

    private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(ConnectionConst.MAX_CONN_TOTAL);
        connectionManager.setDefaultMaxPerRoute(ConnectionConst.MAX_CONN_PER_ROUTE);
        return connectionManager;
    }
}
