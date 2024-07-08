package com.learning.springaitest.controller;

import com.learning.springaitest.model.Question;
import com.learning.springaitest.service.ImageGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageGenerationController {

    @Autowired
    private ImageGenerationService imageGenerationService;

    @PostMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generateImage(@RequestBody Question question){
        return imageGenerationService.generateImage(question);
    }
}
