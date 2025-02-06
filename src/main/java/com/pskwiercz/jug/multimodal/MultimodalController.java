package com.pskwiercz.jug.multimodal;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class MultimodalController {

    @Value("classpath:/images/screenshot.png")
    Resource resource;

    private final ChatModel chatModel;

    public MultimodalController(ChatModel ChatModel) {
        this.chatModel = ChatModel;
    }

    @GetMapping("/imagedescribe")
    public String imageDescribe() throws IOException {
        UserMessage userMessage = new UserMessage("This is a screenshot of some code. Can you provide a description of what this code does?",
                List.of(new Media(MimeTypeUtils.IMAGE_PNG, resource)));
        var response = chatModel.call(new Prompt(userMessage));
        return response.getResult().getOutput().getContent();
    }
}


