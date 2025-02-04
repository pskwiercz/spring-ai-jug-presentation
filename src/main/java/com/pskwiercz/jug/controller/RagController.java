package com.pskwiercz.jug.controller;

import com.pskwiercz.jug.model.Answer;
import com.pskwiercz.jug.model.Question;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RagController {

    @Value("classpath:/templates/prompt-template.st")
    private Resource promptTemplate;

    private final ChatModel chatModel;
    private final VectorStore vectorStore;

    public RagController(ChatModel chatModel, VectorStore vectorStore) {
        this.chatModel = chatModel;
        this.vectorStore = vectorStore;
    }

    @PostMapping("/rag")
    public Answer rag(@RequestBody Question question) {

        List<Document> documents = vectorStore.similaritySearch(SearchRequest.builder()
                .query(question.question())
                .topK(3)
                .build());

        List<String> contentList = documents.stream()
                .map(Document::getText)
                .toList();

        PromptTemplate promptTemplate = new PromptTemplate(this.promptTemplate);
        Prompt prompt = promptTemplate.create(Map.of(
                "input", question.question(),
                "documents", String.join("\n", contentList)));

        contentList.forEach(System.out::println);

        ChatResponse response = chatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }
}
