package org.example.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.LessonNotFoundException;
import org.example.model.Lesson;
import org.example.service.LessonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class LessonServiceTest {

    private final String path = "src/test/resources/forTest.json";
    @Mock
    private ObjectMapper objectMapper;
    private LessonService service;

    private List<Lesson> list;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new LessonServiceImpl(path, objectMapper);
        initListAddTenLessons();
    }

    @Test
    void whenGetAllLessonsThenReturn10Lessons() throws IOException {
        initListAddTenLessons();
        mockGetAllLessons();
        List<Lesson> sut = service.getAll();
        verify(objectMapper, times(1)).readValue(any(FileReader.class), any(TypeReference.class));
        assertEquals(list.size(), sut.size());
    }

    @Test
    void whenGetAllLessonsAndThrowIOExceptionThenReturnEmptyList() throws IOException {
        when(objectMapper.readValue(any(FileReader.class), any(TypeReference.class))).thenThrow(IOException.class);
        List<Lesson> sut = service.getAll();
        verify(objectMapper, times(1)).readValue(any(FileReader.class), any(TypeReference.class));
        assertEquals(Collections.emptyList(), sut);
    }


    @Test
    void whenGetLessonByIdEqualsTwoThenReturnLessonWithIdEqualsTwo() throws Exception {
        final int expectedId = 2;
        mockGetAllLessons();
        Lesson lessonById = service.getById(expectedId);
        assertEquals(expectedId, lessonById.getId());
    }

    @Test
    void whenGetLessonNotExistIdThenThrowLessonNotFoundException() throws IOException {
        final int expectedId = -1;
        mockGetAllLessons();
        assertThrows(LessonNotFoundException.class, () -> service.getById(expectedId));
    }


    @Test
    void whenAddLessonToListThatAlreadyContainsLessonsThenSizeMustBeIncrease() throws IOException {
        mockGetAllLessons();
        assertEquals(10, list.size());
        Lesson lesson = new Lesson(10, "Test", "DescriptionTest", "", "Java");
        doNothing().when(objectMapper).writeValue(any(FileWriter.class), anyList());
        service.add(lesson);
        assertEquals(11, list.size());
    }

    @Test
    void whenAddLessonToEmptyList() throws IOException {
        when(objectMapper.readValue(any(FileReader.class), any(TypeReference.class))).thenReturn(Collections.emptyList());
        doNothing().when(objectMapper).writeValue(any(FileWriter.class), any(Lesson.class));
        service.add(new Lesson());
        verify(objectMapper, times(1)).writeValue(any(FileWriter.class), any(Lesson.class));
    }


    @Test
    void whenSearchLessonByExistTopicThenReturnThisLessons() throws IOException, LessonNotFoundException {
        mockGetAllLessons();
        List<Lesson> lessons = service.searchLessonByTopic("StreamAPI");
        assertEquals(5, lessons.size());
    }


    @Test
    void whenSearchLessonByNotExistTopicThenThrowLessonNotFoundException() throws IOException {
        mockGetAllLessons();
        assertThrows(LessonNotFoundException.class, () -> service.searchLessonByTopic("RandomText"));
    }

    @Test
    void whenSearchLessonByExistTopicButListIsEmptyThenReturnEmptyList() throws IOException, LessonNotFoundException {
        when(objectMapper.readValue(any(FileReader.class), any(TypeReference.class))).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), service.searchLessonByTopic("OOP"));
    }

    @Test
    void whenEditExistLessonThenThisLessonMustBeChange() throws Exception {
        final int id = 1;
        Lesson updatedLesson = new Lesson(id, "newTitle", "newDescription", "", "Java");
        mockGetAllLessons();
        doAnswer(invocation -> {
            Lesson lesson = list.stream().filter(el -> el.getId() == id).findAny().get();
            list.remove(lesson);
            list.add(updatedLesson);
            return null;
        }).when(objectMapper).writeValue(any(FileWriter.class), anyList());

        Lesson sut = service.editById(id, updatedLesson);
        assertTrue(list.contains(sut));
    }

    @Test
    void whenDeleteLessonWithExistId() throws Exception {
        final int id = 1;
        mockGetAllLessons();
        doAnswer(invocation -> {
            Lesson lesson = null;
            for (Lesson ls : list) {
                if (ls.getId() == id) {
                    lesson = ls;
                    break;
                }
            }
            list.remove(lesson);
            return null;
        }).when(objectMapper).writeValue(any(FileWriter.class), anyList());
        boolean sut = service.deleteById(1);
        verify(objectMapper, times(1)).writeValue(any(FileWriter.class), anyList());
        assertEquals(9, list.size());
        assertTrue(sut);
    }

    @Test
    void whenDeleteLessonWithNotExistIdThenThrowLessonNotFoundException() throws IOException, LessonNotFoundException {
        mockGetAllLessons();
        assertThrows(LessonNotFoundException.class, () -> service.deleteById(-1));
    }



    private void mockGetAllLessons() throws IOException {
        when(objectMapper.readValue(any(FileReader.class), any(TypeReference.class))).thenReturn(list);
    }


    private void initListAddTenLessons() {
        String topicOop = "OOP";
        String topicStream = "StreamAPI";
        list = new ArrayList<>();
        String topic;
        for (int i = 0; i < 10; i++) {
            topic = (i % 2 == 0) ? topicOop : topicStream;
            list.add(new Lesson(i + 1 , "Title-" + i, "SomeDescription-" + i, "SomeContent", topic));
        }
    }

}