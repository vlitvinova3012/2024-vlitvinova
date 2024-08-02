package ru.demo.vlitvinovaspringboot.service;


import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.demo.vlitvinovaspringboot.dto.Question;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static ru.demo.vlitvinovaspringboot.util.Constants.*;

@SuppressWarnings("squid:S106")
@Service
@RequiredArgsConstructor
public class IOServiceImpl implements IOService {
    private final MessageSource messageSource;
    @Override
    public void printWelcomeInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(messageSource.getMessage(ENTER_NAME, null, Locale.getDefault()));
        scanner.nextLine();
        System.out.println(messageSource.getMessage(ANSWER_QUESTION, new String[]{"\n"}, Locale.getDefault()));
    }

    @Override
    public void printQuestionWithPossibleAnswers(Question question) {
        System.out.println(question.getQuestionFromFile());
        List<String> answers = question.getAnswers();
        IntStream.range(0, answers.size())
                .forEach(i -> System.out.println((i + 1) + ". " + answers.get(i)));
        System.out.println(messageSource.getMessage(LIST_ANSWERS, null, Locale.getDefault()));
    }

    @Override
    public List<String> getUserAnswers() {
        Scanner scanner = new Scanner(System.in);
        String userAnswers = scanner.nextLine();
        return Arrays.asList(userAnswers.replace(" ", "").split(","));
    }

    @Override
    public void printAnswer(boolean isRightAnswer) {
        if (isRightAnswer) {
            System.out.println(messageSource.getMessage(RIGHT, null, Locale.getDefault()));
        } else {
            System.out.println(messageSource.getMessage(WRONG, null, Locale.getDefault()));
        }
    }

    @Override
    public void splitSections() {
        System.out.println("---------------------------");
    }

    @Override
    public void getResultScore(AtomicInteger score) {
        System.out.println(messageSource.getMessage(SCORE, new AtomicInteger[]{score}, Locale.getDefault()));
    }


}
