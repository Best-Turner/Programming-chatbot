package org.example.service.impl;

import org.example.model.Question;
import org.example.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Override
    public List<Question> getAll() {
        return null;
    }

    @Override
    public Question getById(Integer integer)  {
        return null;
    }

    @Override
    public boolean deleteById(Integer integer)  {
        return false;
    }

    @Override
    public void add(Question question) {

    }

    @Override
    public Question editById(Integer integer, Question question)  {
        return null;
    }
}
