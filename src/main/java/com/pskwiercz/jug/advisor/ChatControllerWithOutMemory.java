package com.pskwiercz.jug.advisor;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatControllerWithOutMemory {

    private final ChatClient chatClient;

    public ChatControllerWithOutMemory(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/nomemory")
    public String memoryChat(@RequestParam String msg) {
        return chatClient.prompt()
                .user(msg)
                .call()
                .content();
    }
}

