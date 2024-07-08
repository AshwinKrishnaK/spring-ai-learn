package com.learning.springaitest.controller;

import com.learning.springaitest.model.Question;
import com.learning.springaitest.service.ImageGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageGenerationController {

    @Autowired
    private ImageGenerationService imageGenerationService;

    @PostMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generateImage(@RequestBody Question question){
        return imageGenerationService.generateImage(question);
    }

    @PostMapping(value = "/image/describe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String describeImage(@Validated @RequestParam("file") MultipartFile file,
                                @RequestParam("prompt") String prompt) throws IOException {
        return  imageGenerationService.getDescription(file, prompt);
    }
}
