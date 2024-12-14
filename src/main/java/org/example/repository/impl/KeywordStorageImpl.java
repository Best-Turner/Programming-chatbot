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
    private final String pathToDeleteLesson;
    private final String pathToEditLesson;
    private final ObjectMapper objectMapper;

    private Set<String> greetingWords;
    private Set<String> keywordsForLesson;
    private Set<String> goodbyeWords;
    private Set<String> keywordsDeleteLesson;
    private Set<String> keywordsEditLesson;
    private Map<Set<String>, Integer> statusCodeMap;

    @Autowired
    public KeywordStorageImpl(@Qualifier("pathForGreetingWords") String pathForGreetingWords,
                              @Qualifier("pathForKeywordsLesson") String pathForKeywordsLesson,
                              @Qualifier("pathForGoodbyeWords") String pathForGoodbyeWords,
                              @Qualifier("deletePath") String pathToDeleteLesson,
                              @Qualifier("pathForEditLesson") String pathToEditLesson,
                              ObjectMapper objectMapper) {
        this.pathForGreetingWords = pathForGreetingWords;
        this.pathForKeywordsLesson = pathForKeywordsLesson;
        this.pathForGoodbyeWords = pathForGoodbyeWords;
        this.pathToDeleteLesson = pathToDeleteLesson;
        this.pathToEditLesson = pathToEditLesson;
        this.objectMapper = objectMapper;
        statusCodeMap = new HashMap<>();
        greetingWords = new HashSet<>();
        keywordsForLesson = new HashSet<>();
        goodbyeWords = new HashSet<>();
        keywordsDeleteLesson = new HashSet<>();
        keywordsEditLesson = new HashSet<>();
        initializeKeyWordsDatabase();

        statusCodeMap.put(greetingWords, 0);
        statusCodeMap.put(keywordsForLesson, 1);
        statusCodeMap.put(keywordsDeleteLesson, 2);
        statusCodeMap.put(keywordsEditLesson, 3);
        //statusCodeMap.put(goodbyeWords, 3);
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
        initDelete();
        initEditLesson();
    }

    private void initEditLesson() {
        try (FileReader reader = new FileReader(pathToEditLesson)) {
            keywordsEditLesson = objectMapper.readValue(reader, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private void initDelete() {
        try (FileReader reader = new FileReader(pathToDeleteLesson)) {
            keywordsDeleteLesson = objectMapper.readValue(reader, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Set<String> getGreetingWords() {
        return greetingWords;
    }

    public Set<String> getKeywordsForLesson() {
        return keywordsForLesson;
    }

    public Set<String> getGoodbyeWords() {
        return goodbyeWords;
    }

    public Set<String> getKeywordsDeleteLesson() {
        return keywordsDeleteLesson;
    }
}
