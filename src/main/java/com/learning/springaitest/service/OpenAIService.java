package com.learning.springaitest.service;

import com.learning.springaitest.model.Song;
import org.springframework.ai.openai.api.common.OpenAiApiException;
import org.springframework.core.ParameterizedTypeReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OpenAIService {

    /**
     * Method to fetch an AI response based on the given message.
     *
     * @param message The message to send to the AI.
     * @return A Map containing the AI's response under the key "response".
     */
    public Map<String, String> openAIResponse(String message) throws OpenAiApiException ;

    /**
     * Retrieves a chat response and its metadata from the AI based on the given message.
     *
     * @param message The message to send to the AI.
     * @return A map containing the AI's response and metadata.
     */
    public Map<String, String> openAIChatResponse(String message) throws OpenAiApiException;

    /**
     * Returning an Entity
     * Example prompt : Generate the details of any bollywood song
     *
     * @return song
     */
    public Song openAIEntityExample(String message) throws OpenAiApiException ;

    /**
     * Returning a list Entity using prompt
     * Example prompt : Generate the details of any 5 bollywood song.
     *
     * @return List of songs
     */
    public List<Song> openAIListEntityExample(String message) throws OpenAiApiException;

    /**
     * Sends a message to the chat client with a specified voice parameter.
     *
     * @param message the message to be sent to the chat client
     * @param voice the voice parameter to be set in the system
     * @return the response content from the chat client
     * Example prompt : message : "tell me a joke", voice : "jack sparrow"
     * */
    public String openAIDefaultSystemExample(String message, String voice) throws OpenAiApiException;

    public Map<String,String> memoryChatExample(String message, String conversationId);
}
