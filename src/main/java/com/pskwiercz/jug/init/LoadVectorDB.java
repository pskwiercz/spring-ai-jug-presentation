package com.pskwiercz.jug.init;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Component
public class LoadVectorDB implements CommandLineRunner {

    @Value("classpath:SpringMCP.pdf")
    Resource resource;
    @Autowired
    private VectorStore vectorStore;

    @Override
    public void run(String... args) throws IOException {


        List<Document> documents = new PagePdfDocumentReader(resource,
                PdfDocumentReaderConfig.builder()
                        .withPageTopMargin(0)
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                .withNumberOfTopTextLinesToDelete(0)
                                .build())
                        .withPagesPerDocument(1)
                        .build())
                .read();

        TextSplitter textSplitter = new TokenTextSplitter();

        for (Document document : documents) {
            vectorStore.add(textSplitter.split(document));
            System.out.println("Added document: " + document.getId());
        }
        System.out.println("Vector store loaded");
    }
}

