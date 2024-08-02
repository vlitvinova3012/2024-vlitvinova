package ru.demo.vlitvinovaspringboot.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;
import ru.demo.vlitvinovaspringboot.dto.Question;
import ru.demo.vlitvinovaspringboot.exception.ResourceNotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionDaoImplTest {
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private QuestionDaoImpl questionDao;

    @Test
    void testGetQuestionListWithValidFile() {
        when(messageSource.getMessage(any(), any(), any())).thenReturn("questions.csv");

        List<Question> questionList = questionDao.getQuestionList();

        Assertions.assertEquals(5, questionList.size());

        Question question1 = questionList.get(0);
        Assertions.assertEquals("Какой сейчас год?", question1.getQuestionFromFile());
        Assertions.assertEquals(Arrays.asList("2022", "2023", "2024"), question1.getAnswers());
        Assertions.assertEquals(List.of("3"), question1.getRightAnswers());
    }

    @Test
    void testGetQuestionListWithInvalidFile() {
        when(messageSource.getMessage(any(), any(), any())).thenReturn("questions_.csv");
        Assertions.assertThrows(ResourceNotFoundException.class, () -> questionDao.getQuestionList());
    }
}