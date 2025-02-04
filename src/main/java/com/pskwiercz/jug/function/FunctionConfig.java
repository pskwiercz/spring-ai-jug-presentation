package com.pskwiercz.jug.function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class FunctionConfig {

    @Bean
    @Description("Get temperature for the proviced city")
    public Function<WeatherService.Request, WeatherService.Response> getTemperature() {
        return new WeatherService();
    }
}
