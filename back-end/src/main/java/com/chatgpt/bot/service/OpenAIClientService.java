package com.chatgpt.bot.service;

import com.chatgpt.bot.model.request.ChatGPTRequest;
import com.chatgpt.bot.model.request.ChatRequest;
import com.chatgpt.bot.openaiclient.OpenAIClientConfig;
import com.chatgpt.bot.model.response.ChatGPTResponse;
import lombok.RequiredArgsConstructor;
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

    @Autowired
    RestTemplate restTemplate;

    public ChatGPTResponse chat(ChatRequest chatRequest) {
        ChatGPTRequest chatGPTRequest = ChatGPTRequest.builder()
                .model(openAIClientConfig.getModel())
                .messages(chatRequest.getMessages())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(openAIClientConfig.getApiKey());
        HttpEntity<ChatGPTRequest> entity = new HttpEntity<ChatGPTRequest>(chatGPTRequest, headers);

        String chatCompletionUrl = openAIClientConfig.getBaseUrl().concat(openAIClientConfig.getChatUrl());
        return restTemplate.exchange(
              chatCompletionUrl, HttpMethod.POST, entity, ChatGPTResponse.class).getBody();
    }

}
