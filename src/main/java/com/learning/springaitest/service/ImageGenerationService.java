package com.learning.springaitest.service;

import com.learning.springaitest.model.Question;
import org.springframework.ai.image.ImageGeneration;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class ImageGenerationService {

    @Autowired
    private OpenAiImageModel imageModel;

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
}
