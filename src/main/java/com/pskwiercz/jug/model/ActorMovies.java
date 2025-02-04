package com.pskwiercz.jug.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"actor", "movies"})
public record ActorMovies(String actor, List<String> movies) {}
