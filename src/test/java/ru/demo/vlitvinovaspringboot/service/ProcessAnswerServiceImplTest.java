package ru.demo.vlitvinovaspringboot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;
import ru.demo.vlitvinovaspringboot.dao.QuestionDao;
import ru.demo.vlitvinovaspringboot.dto.Question;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.demo.vlitvinovaspringboot.util.Constants.RIGHT;
import static ru.demo.vlitvinovaspringboot.util.Constants.WRONG;

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
                Arrays.asList("A programming language", "A coffee"), Arrays.asList("1"));

        when(questionDao.getQuestionList()).thenReturn(Arrays.asList(question1));
        when(ioService.read()).thenReturn( "1");

        Map<String, List<String>> result =  processAnswerService.processAnswers();

        assertFalse(CollectionUtils.isEmpty(result));
        assertNull(result.get(WRONG));
        assertNotNull(result.get(RIGHT));
        verify(ioService, times(3)).out(any());
        verify(ioService, times(4)).outQuestion(any());
        verify(ioService, times(2)).read();
    }
}