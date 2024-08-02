package ru.demo.vlitvinovaspringboot.dao;


import ru.demo.vlitvinovaspringboot.dto.Question;

import java.util.List;

public interface QuestionDao {
    public List<Question> getQuestionList();
}
