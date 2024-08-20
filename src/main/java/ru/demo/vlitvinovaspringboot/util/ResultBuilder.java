package ru.demo.vlitvinovaspringboot.util;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static ru.demo.vlitvinovaspringboot.util.Constants.RIGHT;
import static ru.demo.vlitvinovaspringboot.util.Constants.WRONG;

@Service
public class ResultBuilder {
    public String buildResult(Map<String, List<String>> resul) {
        StringBuilder sb = new StringBuilder();
        sb.append(WRONG);
        sb.append("--");
        sb.append(resul.get(WRONG) == null ? 0 : resul.get(WRONG).size());
        sb.append("\n");
        if (resul.get(WRONG) != null) {
            sb.append(String.join(";\n", resul.get(WRONG)));
        }
        sb.append("\n");
        sb.append(RIGHT);
        sb.append("--");
        sb.append(resul.get(RIGHT) == null ? 0 : resul.get(RIGHT).size());
        sb.append("\n");
        if (resul.get(RIGHT) != null) {
            sb.append(String.join(";\n", resul.get(RIGHT)));
        }
        return sb.toString();
    }
}
