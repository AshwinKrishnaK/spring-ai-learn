package com.learning.springaitest.controller;

import com.learning.springaitest.model.Answer;
import com.learning.springaitest.model.Question;
import com.learning.springaitest.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @PostMapping("/weather")
    public Answer askQuestion(@RequestBody Question question) {
        return weatherService.getAnswer(question);
    }
}
