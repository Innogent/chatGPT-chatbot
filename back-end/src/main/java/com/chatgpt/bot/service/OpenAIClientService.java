package com.chatgpt.bot.service;

import com.chatgpt.bot.model.request.ChatGPTRequest;
import com.chatgpt.bot.model.request.ChatRequest;
import com.chatgpt.bot.openaiclient.OpenAIClientConfig;
import com.chatgpt.bot.model.response.ChatGPTResponse;
import com.chatgpt.bot.restcontroller.OpenAIClientController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class OpenAIClientService {

    private final OpenAIClientConfig openAIClientConfig;

    private static final Logger logger = LoggerFactory.getLogger(OpenAIClientController.class);

    @Autowired
    RestTemplate restTemplate;

    public ChatGPTResponse chat(ChatRequest chatRequest) {
        logger.info("Request from user : {}", chatRequest);
        ChatGPTRequest chatGPTRequest = ChatGPTRequest.builder()
                .model(openAIClientConfig.getModel())
                .messages(chatRequest.getMessages())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(openAIClientConfig.getApiKey());
        HttpEntity<ChatGPTRequest> entity = new HttpEntity<ChatGPTRequest>(chatGPTRequest, headers);

        String chatCompletionUrl = openAIClientConfig.getBaseUrl().concat(openAIClientConfig.getChatUrl());

        logger.info("Request made to chatGPT : {} at url :{}", chatGPTRequest, chatCompletionUrl);
        return restTemplate.exchange(
              chatCompletionUrl, HttpMethod.POST, entity, ChatGPTResponse.class).getBody();
    }

}
