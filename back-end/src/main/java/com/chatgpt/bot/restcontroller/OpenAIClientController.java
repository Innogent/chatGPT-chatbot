package com.chatgpt.bot.restcontroller;

import com.chatgpt.bot.model.request.ChatRequest;
import com.chatgpt.bot.model.response.ChatGPTResponse;
import com.chatgpt.bot.model.response.ErrorResponse;
import com.chatgpt.bot.service.OpenAIClientService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(value = "/api/v1")
public class OpenAIClientController {

    private final OpenAIClientService openAIClientService;

    private static final Logger logger = LoggerFactory.getLogger(OpenAIClientController.class);

    @PostMapping(value = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Object> chat(@RequestBody ChatRequest chatRequest) {
        try{
           ChatGPTResponse chatGPTResponse = openAIClientService.chat(chatRequest);
            logger.info("Response recieved from chatGPT : {}", chatGPTResponse);
           return ResponseEntity.ok().body(chatGPTResponse);
        } catch(HttpStatusCodeException exception) {
            logger.error("Error in response received : {}", exception.getMessage());
            if(exception.getStatusCode().value() == 401){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Your API key is invalid, please retry with another API key"));
            } else if(exception.getStatusCode().value() == 429){
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(new ErrorResponse("You exceeded your current quota, please check your plan and billing details"));
            } else if (exception.getStatusCode().value() == 500) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("The server had an error while processing your request, retry your request after a brief wait"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("INTERNAL SERVER ERROR, please refresh the page and try again"));
            }

        }
    }

}
