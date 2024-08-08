package ru.demo.vlitvinovaspringboot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.demo.vlitvinovaspringboot.dao.QuestionDao;
import ru.demo.vlitvinovaspringboot.dto.Question;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessAnswerServiceImplTest {
    @Mock
    private QuestionDao questionDao;

    @Mock
    private IOService ioService;

    @InjectMocks
    private ProcessAnswerServiceImpl processAnswerService;

    @Test
    void testProcessAnswers() {
        Question question1 = new Question("What is Java?",
                Arrays.asList("A programming language", "A coffee"), Arrays.asList("A programming language"));
        Question question2 = new Question("What is Python?",
                Arrays.asList("A snake", "A programming language"), Arrays.asList("A programming language"));

        when(questionDao.getQuestionList()).thenReturn(Arrays.asList(question1, question2));
        when(ioService.read()).thenReturn("User", "1", "2");

        processAnswerService.processAnswers();

        verify(ioService, times(5)).out(any());
        verify(ioService, times(10)).outQuestion(any());
        verify(ioService, times(3)).read();
    }
}