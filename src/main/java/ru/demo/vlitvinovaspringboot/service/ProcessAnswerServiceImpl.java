package ru.demo.vlitvinovaspringboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.demo.vlitvinovaspringboot.dao.QuestionDao;
import ru.demo.vlitvinovaspringboot.dto.Question;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static ru.demo.vlitvinovaspringboot.util.Constants.*;

@Service
@RequiredArgsConstructor
public class ProcessAnswerServiceImpl implements ProcessAnswerService {

    private final QuestionDao questionDao;
    private final IOService ioService;

    @Override
    public Map<String, List<String>> processAnswers() {
        ioService.out(ENTER_NAME);
        ioService.read();
        ioService.out(ANSWER_QUESTION, "\n");

        List<Question> questionList = questionDao.getQuestionList();
        AtomicInteger score = new AtomicInteger();
        Map<String, List<String>> resultInfo = new HashMap<>();

        questionList.forEach(question -> {
            showQuestion(question);

            boolean isRightAnswer = testUser(question);

            printAnswer(isRightAnswer, resultInfo, question);

            if (isRightAnswer) {
                score.getAndIncrement();
            }
            ioService.outQuestion("---------------------------");
        });
        ioService.out(SCORE, score.toString());
        return resultInfo;
    }

    private void showQuestion(Question question) {
        ioService.outQuestion(question.getQuestionFromFile());
        List<String> answers = question.getAnswers();
        IntStream.range(0, answers.size())
                .forEach(i -> ioService.outQuestion((i + 1) + ". " + answers.get(i)));
        ioService.out(LIST_ANSWERS);
    }

    private void printAnswer(boolean isRightAnswer, Map<String, List<String>> resultInfo, Question question) {
        if (isRightAnswer) {
            ioService.out(RIGHT);
            fillMap(resultInfo, question, RIGHT);
        } else {
            ioService.out(WRONG);
            fillMap(resultInfo, question, WRONG);
        }

    }

    private void fillMap(Map<String, List<String>> resultInfo, Question question, String answer) {
        List<String> questions = new ArrayList<>();
        if (resultInfo.get(answer) != null) {
            questions = resultInfo.get(answer);
        }
        questions.add(question.getQuestionFromFile());
        resultInfo.put(answer, questions);
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
