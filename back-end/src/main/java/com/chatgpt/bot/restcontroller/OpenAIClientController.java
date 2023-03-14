package com.chatgpt.bot.restcontroller;

import com.chatgpt.bot.model.response.ChatGPTResponse;
import com.chatgpt.bot.model.request.ChatRequest;
import com.chatgpt.bot.service.OpenAIClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(value = "/api/v1")
public class OpenAIClientController {

    private final OpenAIClientService openAIClientService;

    @PostMapping(value = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ChatGPTResponse chat(@RequestBody ChatRequest chatRequest) {
        return openAIClientService.chat(chatRequest);
    }

}
