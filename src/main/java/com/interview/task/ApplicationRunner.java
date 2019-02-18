package com.interview.task;

import com.interview.task.config.CurrencyRatesConfig;
import com.interview.task.config.HeaderProperties;
import com.interview.task.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(value = {CurrencyRatesConfig.class, JwtProperties.class, HeaderProperties.class})
@SpringBootApplication
public class ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }
}
