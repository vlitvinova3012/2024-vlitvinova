package ru.demo.vlitvinovaspringboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.demo.vlitvinovaspringboot.service.ProcessAnswerService;
import ru.demo.vlitvinovaspringboot.util.ResultBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class WelcomeController {
    private final ProcessAnswerService service;
    private final ResultBuilder builder;

    @GetMapping("/")
    public String welcome() {
        Map<String, List<String>> result = service.processAnswers();
        return builder.buildResult(result);
    }
}
