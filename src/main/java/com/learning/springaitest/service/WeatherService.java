package com.learning.springaitest.service;

import com.learning.springaitest.function.WeatherServiceFunction;
import com.learning.springaitest.model.Answer;
import com.learning.springaitest.model.Question;
import com.learning.springaitest.model.WeatherResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherService {

    @Autowired
    private ChatClient chatClient;

    @Value("${api.ninjas.key}")
    private String apiNinjaKey;

    public Answer getAnswer(Question question) {
        var promptOptions = OpenAiChatOptions.builder()
                .withFunctionCallbacks(List.of(FunctionCallbackWrapper.builder(new WeatherServiceFunction(apiNinjaKey))
                        .withName("CurrentWeather")
                        .withDescription("Get the current weather for a location")
                        .withResponseConverter((response) -> {
                            String schema = ModelOptionsUtils.getJsonSchema(WeatherResponse.class, false);
                            String json = ModelOptionsUtils.toJsonString(response);
                            return schema + "\n" + json;
                        })
                        .build()))
                .build();

        Message userMessage = new PromptTemplate(question.question()).createMessage();

        var response =  chatClient.prompt(new Prompt(List.of(userMessage), promptOptions))
                        .call()
                        .content();

        return new Answer(response);
    }
}
