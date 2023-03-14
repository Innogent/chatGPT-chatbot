package com.chatgpt.bot.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ChatRequest implements Serializable {
    private List<Message> messages;
}
