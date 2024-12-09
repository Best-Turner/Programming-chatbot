package org.example.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.LessonNotFoundException;
import org.example.model.Lesson;
import org.example.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    private final String lessonsFilePath;
    private final ObjectMapper objectMapper;


    @Autowired
    public LessonServiceImpl(
            @Qualifier("lessonPath") String lessonsFilePath,
            ObjectMapper objectMapper) {
        this.lessonsFilePath = lessonsFilePath;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Lesson> getAll() {
        List<Lesson> lessons;
        try (FileReader reader = new FileReader(lessonsFilePath)) {
            lessons = objectMapper.readValue(reader, new TypeReference<>() {
            });
        } catch (IOException e) {
            lessons = Collections.emptyList();
        }
        return lessons;
    }

    @Override
    public Lesson getById(Integer id) throws LessonNotFoundException {
        List<Lesson> allLessons = getAll();
        Optional<Lesson> lesson = allLessons.stream().filter(ls -> ls.getId() == id).findAny();
        return lesson.orElseThrow(() -> new LessonNotFoundException("Урок с ID - " + id + " не найден"));
    }

    @Override
    public boolean deleteById(Integer id) throws LessonNotFoundException {
        List<Lesson> collect = getAll();
        try (FileWriter writer = new FileWriter(lessonsFilePath)) {
            Optional<Lesson> any = collect.stream().filter(el -> el.getId() == id).findFirst();
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

    @Override

    public Lesson editById(Integer id, Lesson l) throws LessonNotFoundException {
        List<Lesson> lessonList = getAll();
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
        return getById(id);
    }

    @Override
    public void add(Lesson lesson) {
        List<Lesson> allLessons = getAll();
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

    @Override
    public List<Lesson> searchLessonByTopic(String topic) throws LessonNotFoundException {
        List<Lesson> allLessons = getAll();
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

    private void init() throws IOException {
        List<Lesson> lessons = List.of(
                new Lesson(1, "Заголовок1", "Описание1", "Привет1", "OOP"),
                new Lesson(2, "Заголовок2", "Описание2", "Привет2", "Java")
        );
        objectMapper.writeValue(new FileWriter(lessonsFilePath), lessons);
    }


}
