package org.example;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableAutoConfiguration
public class ExampleProviderApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ExampleProviderApplication.class).run(args);
    }
}