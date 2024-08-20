package ru.demo.vlitvinovaspringboot.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;
import ru.demo.vlitvinovaspringboot.dto.Question;
import ru.demo.vlitvinovaspringboot.exception.ResourceNotFoundException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionDaoImplTest {
    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private QuestionDaoImpl questionDao;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(questionDao, "file", "test.filename");
    }

    @Test
    void testGetQuestionList() throws Exception {
        String fileName = "questions.csv";

        when(messageSource.getMessage(any(), any(), any())).thenReturn(fileName);

        List<Question> questions = questionDao.getQuestionList();

        assertNotNull(questions);
        assertEquals(5, questions.size());

        Question question = questions.get(0);
        assertEquals("Какой сейчас год?", question.getQuestionFromFile());
    }

    @Test
    void testGetQuestionListResourceNotFound() {
        String fileName = "noexist.csv";

        when(messageSource.getMessage(any(), any(), any())).thenReturn(fileName);

        assertThrows(ResourceNotFoundException.class, () -> questionDao.getQuestionList());
    }

}