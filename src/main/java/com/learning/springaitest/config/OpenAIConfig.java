package com.learning.springaitest.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class OpenAIConfig {

	@Value("classpath:templates/get-default-prompt.st")
    private Resource defaultPrompt;

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
}
