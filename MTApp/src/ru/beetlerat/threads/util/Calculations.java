package ru.beetlerat.threads.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Calculations {
    public static StringBuilder generalOutput = new StringBuilder();

    public static void run() {
        try {
            Thread.sleep(TimeUnit.MILLISECONDS.toMillis(ThreadLocalRandom.current().nextInt(10, 150)));
        } catch (InterruptedException e) {
            ThreadSafeConsoleOutput.consoleOutput(String.format("\nComputational thread %s error: %s\n", Thread.currentThread(), e));
        }
    }

    public static boolean isStringMatchesPattern(String string, String pattern, int count) {
        String[] threadsPattern = pattern.split("\n");
        int currentStringIndex = 0;

        for (String onePattern : threadsPattern) {
            Map<Character, Integer> charCount = new HashMap<>();
            boolean isStrictSequence = false;
            for (int i = 0; i < onePattern.length(); i++) {
                if (Character.isUpperCase(onePattern.charAt(i))) {
                    isStrictSequence = true;
                    break;
                }
                charCount.put(onePattern.charAt(i), 0);
            }
            int currentPatternIndex = 0;
            while (currentPatternIndex < onePattern.length() * count) {
                if (isStrictSequence) {
                    if (string.charAt(currentStringIndex)!=onePattern.toLowerCase().charAt(currentPatternIndex%count)){
                        return false;
                    }
                } else {

                    if (!charCount.containsKey(string.charAt(currentStringIndex)) || charCount.get(string.charAt(currentStringIndex)) >= count) {
                        return false;
                    }
                    charCount.put(string.charAt(currentStringIndex), charCount.get(string.charAt(currentStringIndex)) + 1);

                }
                currentStringIndex++;
                currentPatternIndex++;
            }

        }
        return true;
    }
}
