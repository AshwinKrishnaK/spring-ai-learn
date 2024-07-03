package com.learning.springaitest.controller;

import java.util.Map;

import com.learning.springaitest.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learning.springaitest.service.OpenAIService;

@RestController
public class OpenAIController {
	
	@Autowired
	private OpenAIService openAIService;

	/**
	 * Endpoint to get a response from OpenAI based on the given message.
	 *
	 * @param message the message to be sent to OpenAI (default is "tell me a joke")
	 * @return a response entity containing the OpenAI response
	 */
	@GetMapping("/completion")
    public ResponseEntity<Map<String, String>> completion(@RequestParam(value = "message", defaultValue = "tell me a joke") String message){
		return ResponseEntity.ok(openAIService.openAIResponse(message));
	}

	/**
	 * Endpoint to get a response from OpenAI with metadata based on the given message.
	 *
	 * @param message the message to be sent to OpenAI (default is "tell me a joke")
	 * @return a response entity containing the OpenAI response with metadata
	 */
	@GetMapping("/completion/metadata")
    public ResponseEntity<Map<String, String>> completionWithMetadata(@RequestParam(value = "message", defaultValue = "tell me a joke") String message){
		return ResponseEntity.ok(openAIService.openAIChatResponse(message));
	}

	/**
	 * Endpoint to get a Song entity response from OpenAI based on the given message.
	 *
	 * @param message the message to be sent to OpenAI (default is "Generate the details of any Bollywood song")
	 * @return a response entity containing the Song entity from OpenAI response
	 */
	@GetMapping("/completion/entity")
	public ResponseEntity<Song> completionWithEntity(@RequestParam(value = "message", defaultValue = "Generate the details of any bollywood song") String message){
		return ResponseEntity.ok(openAIService.openAIEntityExample(message));
	}
	
}

