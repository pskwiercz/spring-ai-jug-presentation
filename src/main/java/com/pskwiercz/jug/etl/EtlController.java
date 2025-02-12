package com.pskwiercz.jug.etl;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.KeywordMetadataEnricher;
import org.springframework.ai.transformer.SummaryMetadataEnricher;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.writer.FileDocumentWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EtlController {

    @Value("classpath:etl-input.txt")
    Resource resource;

    ChatModel chatModel;

    public EtlController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/etl")
    public String etl() {

        // Extract
        TextReader textReader = new TextReader(resource);
        List<Document> documents = textReader.get();

        // Transform
        TextSplitter splitter = new TokenTextSplitter();
        List<Document> splitDocuments = splitter.split(documents);

        KeywordMetadataEnricher keywordMetadataEnricher = new KeywordMetadataEnricher(chatModel,5);
        List<Document> enricherDocuments1 = keywordMetadataEnricher.apply(splitDocuments);

        SummaryMetadataEnricher summaryMetadataEnricher = new SummaryMetadataEnricher(chatModel,List.of(SummaryMetadataEnricher.SummaryType.CURRENT ));
        List<Document> enricherDocuments2 = summaryMetadataEnricher.apply(splitDocuments);


        // Load
        FileDocumentWriter writer = new FileDocumentWriter("output.txt",false, MetadataMode.ALL,false);
        writer.accept(enricherDocuments1);
        writer.accept(enricherDocuments2);

        return "Pipeline finished";
    }

}
