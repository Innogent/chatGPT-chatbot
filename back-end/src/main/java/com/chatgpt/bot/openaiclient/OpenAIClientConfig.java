package com.chatgpt.bot.openaiclient;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Indexed;

@Configuration
@ConfigurationProperties
@Indexed
@Data
@Slf4j
public class OpenAIClientConfig {

    @Value("${openai-service.api-key}")
    private String apiKey;

    @Value("${openai-service.gpt-model}")
    private String model;

    @Value("${openai-service.urls.base-url}")
    private String baseUrl;

    @Value("${openai-service.urls.chat-url}")
    private String chatUrl;

}
