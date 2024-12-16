package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppConfigTest {

    private ApplicationContext context;

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    void testLessonsFilePath() {
        String lessonsFilePath = context.getBean("lessonsFilePath", String.class);
        assertNotNull(lessonsFilePath);
    }

    @Test
    void testDeleteFilePath() {
        String lessonsFilePath = context.getBean("pathForDeleteLesson", String.class);
        assertNotNull(lessonsFilePath);
    }

    @Test
    void testPathForGreetingWords() {
        String greetingWordsPath = context.getBean("pathForGreetingWords", String.class);
        assertNotNull(greetingWordsPath);
    }

    @Test
    void testObjectMapperBean() {
        ObjectMapper objectMapper = context.getBean(ObjectMapper.class);
        assertNotNull(objectMapper);
    }
}