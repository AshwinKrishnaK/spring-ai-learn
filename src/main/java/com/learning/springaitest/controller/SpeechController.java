package com.learning.springaitest.controller;

import com.learning.springaitest.model.Question;
import com.learning.springaitest.service.SpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpeechController {

    @Autowired
    private SpeechService speechService;

    @PostMapping(value = "/speak",produces = "audio/mpeg")
    public byte[] speak(@RequestBody Question question){
        return speechService.speak(question);
    }
}
