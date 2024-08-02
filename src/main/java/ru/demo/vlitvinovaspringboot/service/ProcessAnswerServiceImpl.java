package ru.demo.vlitvinovaspringboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.demo.vlitvinovaspringboot.dao.QuestionDao;
import ru.demo.vlitvinovaspringboot.dto.Question;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ProcessAnswerServiceImpl implements ProcessAnswerService {

    private final QuestionDao questionDao;
    private final IOService ioService;

    @Override
    public void processAnswers() {
        ioService.printWelcomeInfo();
        List<Question> questionList = questionDao.getQuestionList();
        AtomicInteger score = new AtomicInteger();
        questionList.forEach(question -> {
            ioService.printQuestionWithPossibleAnswers(question);
            boolean isRightAnswer = testUser(question);
            ioService.printAnswer(isRightAnswer);
            if (isRightAnswer) {
                score.getAndIncrement();
            }
            ioService.splitSections();
        });
        ioService.getResultScore(score);
    }

    private boolean testUser(Question question) {
        List<String> listAnswers = ioService.getUserAnswers();
        List<String> rightAnswers = question.getRightAnswers();
        if (listAnswers.size() == rightAnswers.size()) {
            Collections.sort(listAnswers);
            Collections.sort(rightAnswers);
            return listAnswers.equals(rightAnswers);
        }
        return false;
    }

}
