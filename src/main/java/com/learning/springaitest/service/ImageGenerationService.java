package com.learning.springaitest.service;

import com.learning.springaitest.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImageGeneration;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class ImageGenerationService {

    @Autowired
    private OpenAiImageModel imageModel;

    @Autowired
    private ChatClient chatClient;

    public byte[] generateImage(Question question){
        var options = OpenAiImageOptions.builder()
                .withQuality("hd")
                .withStyle("natural")
                .withHeight(1024).withWidth(1024)
                .withResponseFormat("b64_json")
                .withModel("dall-e-3")
                .build();
        ImagePrompt imagePrompt = new ImagePrompt(question.question(),options);
        var imageResponse = imageModel.call(imagePrompt);
        return Base64.getDecoder().decode(imageResponse.getResult().getOutput().getB64Json());
    }

    public String getDescription(MultipartFile file,String prompt) throws IOException{
        var options = OpenAiChatOptions.builder()
                .withModel(OpenAiApi.ChatModel.GPT_4_VISION_PREVIEW)
                .build();
        var userMessage = new UserMessage(prompt,
                        List.of(new Media(MimeTypeUtils.IMAGE_JPEG,file.getResource())));
        return chatClient.prompt(new Prompt(userMessage))
                .call()
                .content();
    }
}
