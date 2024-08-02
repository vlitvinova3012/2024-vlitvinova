package ru.demo.vlitvinovaspringboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.demo.vlitvinovaspringboot.service.ProcessAnswerService;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class WelcomeController {
    private final ProcessAnswerService service;

    @GetMapping("/")
    public String welcome() {
        service.processAnswers();
        return "Thank you for taking the test";
    }
}
