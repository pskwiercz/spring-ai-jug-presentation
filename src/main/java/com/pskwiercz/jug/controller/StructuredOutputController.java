package com.pskwiercz.jug.controller;

import com.pskwiercz.jug.model.ActorMovies;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StructuredOutputController {

    private final ChatClient chatClient;

    public StructuredOutputController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/beer")
    List<String> getBeer() {
        return chatClient
                .prompt()
                .user("List five most popular beers on the world")
                .call()
                .entity(new ListOutputConverter(new DefaultConversionService()));
    }

    @GetMapping("/beer/countries")
    Map<String, Object> getBeerCountries() {
        return chatClient
                .prompt()
                .user("Give me five most popular beers in USA, Germany and Poland under they key country name")
                .call()
                .entity(new ParameterizedTypeReference<Map<String, Object>>() {});
    }

    @GetMapping("/movie")
    ActorMovies getMovie(@RequestParam(name = "actor", defaultValue = "Pamela Anderson") String actor) {
        return chatClient
                .prompt()
                .user(userMsg -> userMsg
                        .text("Generate the filmography of 5 movies for {actor}.")
                        .param("actor", actor))
                .call()
                .entity(ActorMovies.class);
    }

    @GetMapping("/movie/list")
    List<ActorMovies> getMovieList() {
        return chatClient
                .prompt()
                .user("Generate filmography of 5 movies for Tom Hanks and Brad Pitt.")
                .call()
                .entity(new ParameterizedTypeReference<List<ActorMovies>>() {});
    }
}
