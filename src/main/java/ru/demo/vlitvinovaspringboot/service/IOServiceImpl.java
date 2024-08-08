package ru.demo.vlitvinovaspringboot.service;


import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class IOServiceImpl implements IOService {
    private final MessageSource messageSource;

    @Override
    public void out(String message, String... params) {
        System.out.println(messageSource.getMessage(message, params, Locale.getDefault()));
    }

    @Override
    public void outQuestion(String message) {
        System.out.println(message);
    }

    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }


}
