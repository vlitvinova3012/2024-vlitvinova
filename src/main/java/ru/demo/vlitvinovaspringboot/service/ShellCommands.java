package ru.demo.vlitvinovaspringboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.demo.vlitvinovaspringboot.util.ResultBuilder;

import java.util.List;
import java.util.Map;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {
    private final ProcessAnswerService processAnswerService;
    private final ResultBuilder builder;

    @ShellMethod("Запуск процесса обработки ответов")
    public String startProcess() {
        Map<String, List<String>> result = processAnswerService.processAnswers();
        return builder.buildResult(result);
    }
}
