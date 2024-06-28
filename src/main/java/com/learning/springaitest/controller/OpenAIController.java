package com.learning.springaitest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learning.springaitest.service.OpenAIService;

@RestController
public class OpenAIController {
	
	@Autowired
	private OpenAIService openAIService;
	
	/**
	 * API for getting AI response
	 * */
	@GetMapping("/completion")
    public Map<String, String> completion(@RequestParam(value = "message", defaultValue = "tell me a joke") String message){
		return openAIService.openAIResponse(message);
	}
	
	/**
	 * API for getting both metadata and response
	 * */
	@GetMapping("/completion/metadata")
    public Map<String, String> completionWithMetadata(@RequestParam(value = "message", defaultValue = "tell me a joke") String message){
		return openAIService.openAIChatResponse(message);
	}
	
}

