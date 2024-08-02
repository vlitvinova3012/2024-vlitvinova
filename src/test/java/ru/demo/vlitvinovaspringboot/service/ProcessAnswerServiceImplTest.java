package ru.demo.vlitvinovaspringboot.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.demo.vlitvinovaspringboot.dao.QuestionDao;
import ru.demo.vlitvinovaspringboot.dto.Question;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessAnswerServiceImplTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @Mock
    private QuestionDao questionDao;

    @Mock
    private IOService ioService;

    ProcessAnswerServiceImpl processAnswerService;

    @BeforeEach
    public void setUp() {
        processAnswerService = new ProcessAnswerServiceImpl(questionDao, ioService);
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testProcessAnswers() {
        List<Question> questionList = new ArrayList<>();
        Question question1 = new Question("Question 1", new ArrayList<>(), new ArrayList<>());
        Question question2 = new Question("Question 2", new ArrayList<>(), new ArrayList<>());
        questionList.add(question1);
        questionList.add(question2);

        when(questionDao.getQuestionList()).thenReturn(questionList);
        when(ioService.getUserAnswers()).thenReturn(new ArrayList<>());

        processAnswerService.processAnswers();

        verify(ioService).printWelcomeInfo();
        verify(questionDao).getQuestionList();
        verify(ioService, times(2)).printQuestionWithPossibleAnswers(any(Question.class));
        verify(ioService, times(2)).printAnswer(anyBoolean());
        verify(ioService, times(2)).splitSections();
        verify(ioService).getResultScore(any(AtomicInteger.class));

    }
}