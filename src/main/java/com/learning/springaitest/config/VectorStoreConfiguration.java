package com.learning.springaitest.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class VectorStoreConfiguration {

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    SimpleVectorStore simpleVectorStores(EmbeddingModel embedding, VectorStoreProperties vectorStoreProperties) {
        var simpleVectorStore = new SimpleVectorStore(embedding);
        File vectorStoreFile = new File(vectorStoreProperties.getVectorStorePath());
        if (vectorStoreFile.exists()) {
            simpleVectorStore.load(vectorStoreFile);
        } else {
            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                System.out.println(document.getFilename());
            });
            var config = PdfDocumentReaderConfig.builder()
                    .withPageExtractedTextFormatter(
                            new ExtractedTextFormatter.Builder()
                                    .build())
                    .build();
            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                log.info(document.getFilename());
                var pdfReader = new PagePdfDocumentReader(document, config);
                TextSplitter textSplitter = new TokenTextSplitter();
                simpleVectorStore.accept(textSplitter.apply(pdfReader.get()));
            });
            simpleVectorStore.save(vectorStoreFile);
            return simpleVectorStore;
        }
        return simpleVectorStore;
    }
}