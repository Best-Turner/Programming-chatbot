package org.example.service.strategy;

import java.util.Arrays;
import java.util.Optional;

public interface ResponseStrategy {
    String response(String input);

    default Optional<Integer> parseInputToInt(String input) {
        String[] split = input.split("\\s+");
        return Arrays.stream(split).filter(el -> el.matches("\\d+")).map(Integer::valueOf).findFirst();
    }
}
