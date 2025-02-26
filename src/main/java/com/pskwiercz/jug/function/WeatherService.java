package com.pskwiercz.jug.function;

import java.util.function.Function;

public class WeatherService implements Function<WeatherService.Request, WeatherService.Response> {

    public record Request(String city) {
    }

    public record Response(String city, String temperature) {
    }

    @Override
    public Response apply(Request request) {
        // Call some API, run SQL query, etc.
        String temp;

        if (request.city().equals("Bydgoszcz")) {
            temp = "27";
        } else {
            temp = "10";
        }

        return new Response(request.city, temp);
    }
}
