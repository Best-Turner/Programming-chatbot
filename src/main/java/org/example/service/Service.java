package org.example.service;

import org.example.exception.LessonNotFoundException;

import java.util.List;

public interface Service<E, T> {

    List<E> getAll();

    E getById(T t) throws Exception;

    boolean deleteById(T t) throws Exception;

    void add(E e);

    E editById(T t, E e) throws LessonNotFoundException;
}
