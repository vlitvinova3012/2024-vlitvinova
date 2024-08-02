package ru.demo.vlitvinovaspringboot.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.demo.vlitvinovaspringboot.dto.Question;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IOServiceImplTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @Mock
    private Question question;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    IOServiceImpl ioService;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testPrintWelcomeInfo() {
        String input = "John Doe";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        when(messageSource.getMessage(any(), any(), any()))
                .thenReturn("Enter your name and surname")
                .thenReturn("Please, answer the questions below.");

        ioService.printWelcomeInfo();

        String expectedOutput = "Enter your name and surname\r\nPlease, answer the questions below.";
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void testPrintQuestionWithPossibleAnswers() {
        String questionFromFile = "What is your favorite color?";
        List<String> answers = Arrays.asList("Red", "Blue", "Green");
        when(question.getQuestionFromFile()).thenReturn(questionFromFile);
        when(question.getAnswers()).thenReturn(answers);
        when(messageSource.getMessage(any(), any(), any()))
                .thenReturn("Please, list number of answers, using \",\"");

        ioService.printQuestionWithPossibleAnswers(question);

        String expectedOutput = "What is your favorite color?\r\n1. Red\r\n2. Blue\r\n3. Green\r\nPlease, list number of answers, using \",\"";
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void testGetUserAnswers() {
        String input = "1, 2, 3";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        List<String> userAnswers = ioService.getUserAnswers();

        List<String> expectedAnswers = Arrays.asList("1", "2", "3");
        assertEquals(expectedAnswers, userAnswers);
    }

    @Test
    void testPrintAnswer() {
        when(messageSource.getMessage(any(), any(), any())).thenReturn("Right");
        ioService.printAnswer(true);
        String expectedOutput1 = "Right";
        assertEquals(expectedOutput1, outputStream.toString().trim());

        outputStream.reset();
        when(messageSource.getMessage(any(), any(), any())).thenReturn("Wrong");
        ioService.printAnswer(false);
        String expectedOutput2 = "Wrong";
        assertEquals(expectedOutput2, outputStream.toString().trim());
    }

    @Test
    void testSplitSections() {
        ioService.splitSections();

        String expectedOutput = "---------------------------";
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void testGetResultScore() {
        AtomicInteger score = new AtomicInteger(3);
        when(messageSource.getMessage(any(), any(), any())).thenReturn("Your score 3/5");

        ioService.getResultScore(score);

        String expectedOutput = "Your score 3/5";
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

}