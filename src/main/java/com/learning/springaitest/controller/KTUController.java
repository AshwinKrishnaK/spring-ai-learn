package com.learning.springaitest.controller;

import com.learning.springaitest.model.Answer;
import com.learning.springaitest.model.Question;
import com.learning.springaitest.service.KTUS1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KTUController {

    @Autowired
    private KTUS1 ktus1;

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return ktus1.syllabusDetails(question);
    }
}
