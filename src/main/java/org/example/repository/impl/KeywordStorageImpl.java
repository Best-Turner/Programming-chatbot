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
    private final String pathToDeleteLesson;
    private final String pathToEditLesson;
    private final ObjectMapper objectMapper;

    private Map<Set<String>, Integer> statusCodeMap = new HashMap<>();
    private Map<String, Set<String>> keywordSets = new HashMap<>();

    @Autowired
    public KeywordStorageImpl(@Qualifier("pathForGreetingWords") String pathForGreetingWords,
                              @Qualifier("pathForKeywordsLesson") String pathForKeywordsLesson,
                              @Qualifier("deletePath") String pathToDeleteLesson,
                              @Qualifier("pathForEditLesson") String pathToEditLesson,
                              ObjectMapper objectMapper) {
        this.pathForGreetingWords = pathForGreetingWords;
        this.pathForKeywordsLesson = pathForKeywordsLesson;
        this.pathToDeleteLesson = pathToDeleteLesson;
        this.pathToEditLesson = pathToEditLesson;
        this.objectMapper = objectMapper;
        initializeKeyWordsDatabase();
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
        keywordSets.put("greetingWords", new HashSet<>());
        keywordSets.put("keywordsForLesson", new HashSet<>());
        keywordSets.put("keywordsDeleteLesson", new HashSet<>());
        keywordSets.put("keywordsEditLesson", new HashSet<>());

        initDictionary(pathForGreetingWords, keywordSets.get("greetingWords"), 0);
        initDictionary(pathForKeywordsLesson, keywordSets.get("keywordsForLesson"), 1);
        initDictionary(pathToDeleteLesson, keywordSets.get("keywordsDeleteLesson"), 2);
        initDictionary(pathToEditLesson, keywordSets.get("keywordsEditLesson"), 3);
    }

    private void initDictionary(String path, Set<String> dictionary, int statusCode) {
        try (FileReader reader = new FileReader(path)) {
            Set<String> loadedData = objectMapper.readValue(reader, new TypeReference<>() {
            });
            dictionary.addAll(loadedData);
            statusCodeMap.put(dictionary, statusCode);
        } catch (IOException e) {
            throw new RuntimeException("Error loading keywords from path: " + path, e);
        }
    }
}
