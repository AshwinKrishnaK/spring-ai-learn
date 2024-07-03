package com.learning.springaitest.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.ai.reader.tika.TikaDocumentReader;

@Configuration
public class OpenAIConfig {

	@Value("classpath:templates/get-default-prompt.st")
    private Resource defaultPrompt;

    @Value("classpath:/BurgerBattle-rules.pdf")
    private Resource rules;

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
		return builder.build();
	}

    @Bean
    ChatClient friendlyVoiceChatClient(ChatClient.Builder builder) {

        return builder.defaultSystem("You are a friendly chat bot that answers question in the voice of a {voice}")
                .build();
    }
    
    @Bean
    ChatClient friendlyVoiceChatClientUsingTemplate(ChatClient.Builder builder) {
        return builder.defaultSystem(defaultPrompt)
                .build();
    }

    @Bean
    ChatClient chatClientChatClient(ChatClient.Builder builder,VectorStore vectorStore) {

        return  builder.defaultAdvisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults())).build();
    }



    @Bean
    public ApplicationRunner go(VectorStore vectorStore) {
        return args -> {
            vectorStore.add(new TokenTextSplitter().apply(new TikaDocumentReader(rules).get()));
        };
    }

}
