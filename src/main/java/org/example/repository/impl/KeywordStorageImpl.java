package org.example.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.repository.KeywordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
public class KeywordStorageImpl implements KeywordStorage {
    private final String pathForGreetingWords;
    private final String pathForKeywordsLesson;
    private final String pathForGoodbyeWords;
    private Set<String> greetingWords;
    private Set<String> keywordsForLesson;
    private Set<String> keywordsForQuestions;
    private Set<String> goodbyeWords;
    private Map<Set<String>, Integer> statusCodeMap;
    private final ObjectMapper objectMapper;

    @Autowired
    public KeywordStorageImpl(@Qualifier("pathForGreetingWords") String pathForGreetingWords,
                              @Qualifier("pathForKeywordsLesson") String pathForKeywordsLesson,
                              @Qualifier("pathForGoodbyeWords") String pathForGoodbyeWords,
                              ObjectMapper objectMapper) {
        this.pathForGreetingWords = pathForGreetingWords;
        this.pathForKeywordsLesson = pathForKeywordsLesson;
        this.pathForGoodbyeWords = pathForGoodbyeWords;
        this.objectMapper = objectMapper;
        statusCodeMap = new HashMap<>();
        greetingWords = new HashSet<>();
        keywordsForLesson = new HashSet<>();
        keywordsForQuestions = new HashSet<>();
        goodbyeWords = new HashSet<>();
        initializeKeyWordsDatabase();

        statusCodeMap.put(greetingWords, 0);
        statusCodeMap.put(keywordsForLesson, 1);
        statusCodeMap.put(keywordsForQuestions, 2);
        statusCodeMap.put(goodbyeWords, 3);
    }

    @Override
    public void add(String key, int command) {

    }

    @Override
    public void deleteCommand(String key) {

    }

    @Override
    public int getStatusCode(String input) {
        return statusCodeMap.entrySet().stream()
                .filter(el -> el.getKey().contains(input))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(-1);
    }

    private void initializeKeyWordsDatabase() {
        initGreeting();
        initLessons();
        initGoodbye();
    }

    private void initGoodbye() {
        try (FileReader reader = new FileReader(pathForGoodbyeWords)) {
            goodbyeWords = objectMapper.readValue(reader, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void initLessons() {
        try (FileReader reader = new FileReader(pathForKeywordsLesson)) {
            keywordsForLesson = objectMapper.readValue(reader, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initGreeting() {
        try (FileReader reader = new FileReader(pathForGreetingWords)) {
            greetingWords = objectMapper.readValue(reader, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
