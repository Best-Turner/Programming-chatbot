package org.example.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.LessonNotFoundException;
import org.example.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private final String lessonsFilePath;

    private final ObjectMapper objectMapper;

    @Autowired
    public LessonService(String lessonsFilePath, ObjectMapper objectMapper) {
        this.lessonsFilePath = lessonsFilePath;
        this.objectMapper = objectMapper;
    }

    public List<Lesson> getAllLessons() {
        List<Lesson> lessons;
        try (FileReader reader = new FileReader(lessonsFilePath)) {
            lessons = objectMapper.readValue(reader, new TypeReference<>() {
            });
        } catch (IOException e) {
            lessons = Collections.emptyList();
        }
        return lessons;
    }


    public Lesson getLessonById(int id) throws LessonNotFoundException {
        List<Lesson> allLessons = getAllLessons();
        Optional<Lesson> lesson = allLessons.stream().filter(ls -> ls.getId() == id).findAny();
        return lesson.orElseThrow(() -> new LessonNotFoundException("Урок с ID - " + id + " не найден"));
    }

    public boolean deleteLessonById(int id) throws LessonNotFoundException {
        try (FileWriter writer = new FileWriter(lessonsFilePath)) {
            List<Lesson> collect = getAllLessons();

            Optional<Lesson> any = collect.stream().filter(el -> el.getId() == id).findAny();
            if (any.isPresent()) {
                collect.remove(any.get());
                objectMapper.writeValue(writer, collect);
                return true;
            }
            throw new LessonNotFoundException("Урок с ID - " + id + " не найден");
        } catch (IOException e) {
            System.out.println("Ошибка записи " + e.getMessage());
        }
        return false;
    }

    public Lesson editLessonById(int id, Lesson l) throws LessonNotFoundException {
        List<Lesson> lessonList = getAllLessons();
        try (FileWriter writer = new FileWriter(lessonsFilePath)) {
            Optional<Lesson> any = lessonList.stream().filter(el -> el.getId() == id).findAny();
            Lesson lesson = any.orElseThrow(() -> new LessonNotFoundException("Урок с ID - " + id + " не найден"));
            lesson.setTitle(l.getTitle());
            lesson.setDescription(l.getDescription());
            lesson.setContent(l.getContent());
            lesson.setTopic(l.getTopic());
            objectMapper.writeValue(writer, lessonList);
        } catch (IOException e) {
            System.out.println("Ошибка записи " + e.getMessage());
        }
        return getLessonById(id);
    }

    public void addLesson(Lesson lesson) {
        List<Lesson> allLessons = getAllLessons();
        try (FileWriter writer = new FileWriter(lessonsFilePath)) {
            if (allLessons.isEmpty()) {
                objectMapper.writeValue(writer, lesson);
            } else {
                allLessons.add(lesson);
                objectMapper.writeValue(writer, allLessons);
            }
        } catch (IOException e) {
            System.out.println("Ошибка записи " + e.getMessage());
        }
    }

    public List<Lesson> searchLessonByTopic(String topic) throws LessonNotFoundException {
        List<Lesson> allLessons = getAllLessons();
        if (allLessons.isEmpty()) {
            return Collections.emptyList();
        }
        boolean existByTopic = allLessons.stream().anyMatch(el -> el.getTopic().equalsIgnoreCase(topic));
        if (!existByTopic) {
            throw new LessonNotFoundException("Задачи по теме - '" + topic + "' не найдено");
        }
        return allLessons.stream()
                .filter(lesson -> lesson.getTopic().equalsIgnoreCase(topic))
                .collect(Collectors.toList());
    }
}
