package com.pskwiercz.jug.function;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {

    private final ChatModel chatModel;

    public WeatherController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/temp")
    String temp(@RequestParam(name = "city", defaultValue = "Warsaw") String city) {

        UserMessage userMessage =
                new UserMessage("What is temperature in " + city);

        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .function("getTemperature")
                .build();

        ChatResponse chatResponse = chatModel
                .call(new Prompt(List.of(userMessage), chatOptions));

        return chatResponse.getResult().getOutput().getContent();
    }
}
