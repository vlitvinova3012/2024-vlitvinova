package ru.demo.vlitvinovaspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    String questionFromFile;
    List<String> answers;
    List<String> rightAnswers;
}
