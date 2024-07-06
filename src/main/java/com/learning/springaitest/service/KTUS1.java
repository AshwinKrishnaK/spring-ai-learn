package com.learning.springaitest.service;


import com.learning.springaitest.model.Answer;
import com.learning.springaitest.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KTUS1 {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private SimpleVectorStore vectorStore;

    @Value("classpath:/templates/get-ktu-syallabus-template.st")
    private Resource ragPromptTemplate;

    @Value("classpath:/templates/system-message-template.st")
    private Resource systemMessageTemplate;

    public Answer syllabusDetails(Question question){
        PromptTemplate systemMessagePromptTemplate = new SystemPromptTemplate(systemMessageTemplate);
        Message systemMessage = systemMessagePromptTemplate.createMessage();
        List<Document> documents = vectorStore.similaritySearch(SearchRequest.
                query(question.question()).withTopK(5));
        List<String> contentList = documents.stream().map(Document::getContent).toList();
        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Message message = promptTemplate.createMessage(Map.of("input", question.question(), "documents",
                String.join("\n", contentList)));
        var response = chatClient
                .prompt()
                .messages(systemMessage,message)
                .call()
                .content();
        return new Answer(response);
    }
}
