package ru.beetlerat.threads.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Calculations {
    public static void performCalculations() {
        try {
            int delay = ThreadLocalRandom.current().nextInt(10, 200);
            Thread.sleep(TimeUnit.MILLISECONDS.toMillis(delay));
            if (ThreadLocalRandom.current().nextInt(0, 100) > 10) {
                Thread.sleep(TimeUnit.MILLISECONDS.toMillis(delay));
            }
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
                    char expectedChar = onePattern.toLowerCase().charAt(currentPatternIndex % count);

                    if (string.charAt(currentStringIndex) != expectedChar) {

                        ThreadSafeConsoleOutput.consoleOutput(String.format("Not correct result: %s except %s\n",
                                string.substring(0, currentStringIndex) + Character.toUpperCase(string.charAt(currentStringIndex)),
                                string.substring(0, currentStringIndex) + Character.toUpperCase(expectedChar)));

                        return false;
                    }

                } else {

                    if (!charCount.containsKey(string.charAt(currentStringIndex)) || charCount.get(string.charAt(currentStringIndex)) >= count) {

                        ThreadSafeConsoleOutput.consoleOutput(String.format("Not correct result: %s except one of \"%s\"\n",
                                string.substring(0, currentStringIndex) + Character.toUpperCase(string.charAt(currentStringIndex)),
                                onePattern));

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
