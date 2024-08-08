package ru.demo.vlitvinovaspringboot.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.demo.vlitvinovaspringboot.dto.Question;
import ru.demo.vlitvinovaspringboot.exception.ResourceNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {
    private final MessageSource messageSource;

    @Value("${application.filename}")
    private String file;

    @Override
    public List<Question> getQuestionList() {
        String fileName = messageSource.getMessage(file, null, Locale.getDefault());
        try (InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new ResourceNotFoundException("Resource not found: " + fileName);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            return reader.lines()
                    .map(line -> line.split(";"))
                    .map(parts -> {
                        String question = parts[0];
                        String[] answers = Arrays.copyOfRange(parts, 1, 4);
                        String[] rightAnswers = Arrays.copyOfRange(parts, 4, parts.length);
                        return new Question(question, Arrays.asList(answers), Arrays.asList(rightAnswers));
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
}
