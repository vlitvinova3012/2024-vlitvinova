package ru.demo.vlitvinovaspringboot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IOServiceImplTest {
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private IOServiceImpl ioService;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testOut() {
        String messageKey = "test.message";
        String message = "Test message";
        String[] params = {"param1", "param2"};

        when(messageSource.getMessage(any(), any(), any())).thenReturn(message);

        ioService.out(messageKey, params);

        assertEquals(message + System.lineSeparator(), outContent.toString());
    }

    @Test
    void testOutQuestion() {
        String question = "What is your name?";

        ioService.outQuestion(question);

        assertEquals(question + System.lineSeparator(), outContent.toString());
    }

    @Test
    void testRead() {
        String input = "User input";
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);

        String result = ioService.read();

        assertEquals(input, result);
    }

}