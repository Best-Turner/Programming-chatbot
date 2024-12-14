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

    @Value("${keywords.greeting.path}")
    private String pathForGreetingWords;
    @Value("${keywords.lesson.path}")
    private String pathForKeywordsLesson;

    @Value("${keywords.goodbye.path}")
    private String pathForGoodbyeWords;

    @Value("${keywords.delete.path}")
    private String pathForDeleteLesson;
    @Value("${keywords.edit.path}")
    private String pathForEditLesson;


    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Qualifier("deletePath")
    public String pathForDeleteLesson() {
        return pathForDeleteLesson;
    }

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
    @Qualifier("pathForGreetingWords")
    public String pathForGreetingWords() {
        return pathForGreetingWords;
    }

    @Bean
    @Qualifier("pathForKeywordsLesson")
    public String pathForKeywordsLesson() {
        return pathForKeywordsLesson;
    }

    @Bean
    @Qualifier("pathForGoodbyeWords")
    public String pathForGoodbyeWords() {
        return pathForGoodbyeWords;
    }

    @Bean
    @Qualifier("pathForEditLesson")
    public String pathForEditLesson() {
        return pathForEditLesson;
    }

}
