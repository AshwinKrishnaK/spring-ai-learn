package com.learning.springaitest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.learning.springaitest.model.Song;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OpenAIService {

	@Autowired
	private ChatClient chatClient;

	@Autowired
	private ChatClient friendlyVoiceChatClient;

	/**
	 * Returning direct content from OpenAI
	 */
	public Map<String, String> openAIResponse(String message) {
		//openAIEntityExample1("");
		//openAIChatResponse(message);
		openAIDefaultSystem("tell me a joke","jack sparrow");
		return Map.of("response", chatClient.prompt().user(message).call().content());
	}

	/**
	 * Returning chatResponse from OpenAI ChatResponse returns actual response and
	 * metadata about how the response was generated mainly includes number of
	 * tokens (both completion and prompt token), Rate Limit, etc
	 */
	public Map<String, String> openAIChatResponse(String message) {
		var chatResponse = chatClient.prompt().user(message).call().chatResponse();
	//	log.info(chatResponse.getMetadata().toString());
		chatResponse.getMetadata().getUsage().getTotalTokens();
		var response = new HashMap<String,String>();
		response.put("response", chatResponse.getResult().getOutput().getContent());
		response.put("metadata", chatResponse.getMetadata().toString());
		return response;
	}

	/**
	 * Returning an Entity
	 */
	public void openAIEntityExample(String message) {
		Song song = chatClient.prompt().user("Generate the details of any bollywood song.").call().entity(Song.class);
	//	log.info(song.toString());
	}

	/**
	 * Returning an Entity
	 */
	public void openAIEntityExample1(String message) {
		List<Song> songs = chatClient.prompt().user("Generate the details of any 5 bollywood song.").call()
				.entity(new ParameterizedTypeReference<List<Song>>() {
				});
	//	log.info(songs.toString());
	}

	public void openAIDefaultSystem(String message, String voice) {
		var response =	friendlyVoiceChatClient
				.prompt()
				.system(s-> s.param("voice", voice))
				.user(message)
				.call()
				.content();
		log.info(response);
	}
}
