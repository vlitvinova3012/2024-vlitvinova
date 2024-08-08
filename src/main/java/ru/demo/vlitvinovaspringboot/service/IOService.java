package ru.demo.vlitvinovaspringboot.service;


public interface IOService {
    void out(String message, String ... params);
    void outQuestion(String message);

    String read();

}
