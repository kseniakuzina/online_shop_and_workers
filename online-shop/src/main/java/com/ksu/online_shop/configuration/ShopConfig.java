package com.ksu.online_shop.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Configuration
public class ShopConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .interceptors((request, body, execution) -> {
                    request.getHeaders().remove("Cookie");
                    String credentials = "service-account:secure-password";
                    String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
                    request.getHeaders().add("Authorization", "Basic " + encodedCredentials);

                    return execution.execute(request, body);
                })
                .build();
    }
}