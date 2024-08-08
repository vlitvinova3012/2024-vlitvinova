package ru.demo.vlitvinovaspringboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.demo.vlitvinovaspringboot.dao.QuestionDao;
import ru.demo.vlitvinovaspringboot.dto.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static ru.demo.vlitvinovaspringboot.util.Constants.*;

@Service
@RequiredArgsConstructor
public class ProcessAnswerServiceImpl implements ProcessAnswerService {

    private final QuestionDao questionDao;
    private final IOService ioService;

    @Override
    public void processAnswers() {
        ioService.out(ENTER_NAME);
        ioService.read();
        ioService.out(ANSWER_QUESTION, "\n");

        List<Question> questionList = questionDao.getQuestionList();
        AtomicInteger score = new AtomicInteger();
        List<String> resultInfo = new ArrayList<>();

        questionList.forEach(question -> {
            showQuestion(question);

            boolean isRightAnswer = testUser(question);

            printAnswer(isRightAnswer, resultInfo, question);

            if (isRightAnswer) {
                score.getAndIncrement();
            }
            ioService.outQuestion("---------------------------");
        });
        resultInfo.forEach(info -> ioService.outQuestion(info + "\n"));
        ioService.out(SCORE, score.toString());
    }

    private void showQuestion(Question question) {
        ioService.outQuestion(question.getQuestionFromFile());
        List<String> answers = question.getAnswers();
        IntStream.range(0, answers.size())
                .forEach(i -> ioService.outQuestion((i + 1) + ". " + answers.get(i)));
        ioService.out(LIST_ANSWERS);
    }

    private void printAnswer(boolean isRightAnswer, List<String> resultInfo, Question question) {
        StringBuilder sb = new StringBuilder();
        sb.append(question.getQuestionFromFile());
        sb.append("-");
        if (isRightAnswer) {
            ioService.out(RIGHT);
            sb.append(RIGHT);
        } else {
            ioService.out(WRONG);
            sb.append(WRONG);
        }
        resultInfo.add(sb.toString());
    }

    private boolean testUser(Question question) {
        List<String> listAnswers = Arrays.asList(ioService.read()
                .replace(" ", "").split(","));
        List<String> rightAnswers = question.getRightAnswers();
        if (listAnswers.size() == rightAnswers.size()) {
            Collections.sort(listAnswers);
            Collections.sort(rightAnswers);
            return listAnswers.equals(rightAnswers);
        }
        return false;
    }

}
