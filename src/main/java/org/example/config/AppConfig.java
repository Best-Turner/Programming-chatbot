package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = "org.example")
@PropertySource("classpath:config.properties")
public class AppConfig {


    @Value("${lessons.file.path}")
    private String lessonsFilePath;

    @Value("${questions.file.path}")
    private String questionFilePath;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @Qualifier("lessonPath")
    public String lessonsFilePath() {
        return lessonsFilePath;
    }

    @Bean
    public String questionFilePath() {
        return questionFilePath;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
