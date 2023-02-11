package com.container.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.container.demo.domain.ExternalPropHolder;

@Configuration
public class AppConfig {

    @Value("${app.sbd.prop1}")
    private String prop1;

    @Value("${app.sbd.prop2}")
    private String prop2;

    @Value("${app.sbd.pass}")
    private String pass;

    @Bean
    public ExternalPropHolder externalPropHolder() {
        return ExternalPropHolder.builder()
            .prop1(prop1)
            .prop2(prop2)
            .pass(pass)
            .build();
    }

}
