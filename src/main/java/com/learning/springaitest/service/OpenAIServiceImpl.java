package com.learning.springaitest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.openai.api.common.OpenAiApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.learning.springaitest.model.Song;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OpenAIServiceImpl implements OpenAIService{

	@Autowired
	private ChatClient chatClient;

	@Autowired
	private ChatMemory chatMemory;

	/**
	 * This chatClient bean is already configured with default message
	 * */
	@Autowired
	private ChatClient friendlyVoiceChatClient;

	@Override
	public Map<String, String> openAIResponse(String message) throws OpenAiApiException {
		String responseContent = chatClient.prompt().user(message).call().content();
		Map<String, String> responseMap = new HashMap<>();
		responseMap.put("response", responseContent);
		return responseMap;
	}

	@Override
	public Map<String, String> openAIChatResponse(String message) throws OpenAiApiException {
		var chatResponse = chatClient.prompt().user(message).call().chatResponse();
		String responseContent = chatResponse.getResult().getOutput().getContent();
		String metadata = chatResponse.getMetadata().toString();
		Map<String, String> response = new HashMap<>();
		response.put("response", responseContent);
		response.put("metadata", metadata);

		return response;
	}

	@Override
	public Song openAIEntityExample(String message) throws OpenAiApiException {
		Song song = chatClient.prompt().user(message).call().entity(Song.class);
		log.info(song.toString());
		return song;
	}

	@Override
	public List<Song> openAIListEntityExample(String message) throws OpenAiApiException {
		List<Song> songs = chatClient.prompt().user(message).call()
				.entity(new ParameterizedTypeReference<List<Song>>() {
				});
		log.info(songs.toString());
		return songs;
	}

	@Override
	public String openAIDefaultSystemExample(String message, String voice) throws OpenAiApiException {
		var response =	friendlyVoiceChatClient
				.prompt()
				.system(s-> s.param("voice", voice))
				.user(message)
				.call()
				.content();
		log.info(response);
		return response;
	}

	public Map<String,String> memoryChatExample(String message, String conversationId){
		Map<String,String> response = new HashMap<>();
		if(conversationId == null || conversationId.isEmpty()){
			conversationId = UUID.randomUUID().toString();
		}
		var content = chatClient.prompt()
				.advisors(new MessageChatMemoryAdvisor(chatMemory ,conversationId,5))
				.user(message)
				.call()
				.content();
		response.put("conversionId",conversationId);
		response.put("content",content);
		return response;
	}
}
