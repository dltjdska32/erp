package com.example.erp.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ChatGPTConfig {

    @Value("${gpt.api.key}")
    private String apiKey;


    @Bean(name="gptRestTemplate")
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate();
        //요청헤더 설정
        restTemplate.getInterceptors().add(((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + apiKey);
            request.getHeaders().add("Content-Type", "application/json");
            return execution.execute(request, body);
        }));

        return restTemplate;
    }
}
