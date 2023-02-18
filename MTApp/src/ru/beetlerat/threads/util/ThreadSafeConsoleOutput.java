package ru.beetlerat.threads.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeConsoleOutput {
    private static final Lock consoleMutex = new ReentrantLock();
    private static final Lock generalOutputMutex = new ReentrantLock();
    private static final StringBuilder generalOutput = new StringBuilder();

    public static void consoleOutput(String string) {
        consoleMutex.lock();
        System.out.print(string);
        consoleMutex.unlock();
    }

    public static void appendToGeneralOutput(String string) {
        generalOutputMutex.lock();
        generalOutput.append(string);
        generalOutputMutex.unlock();
    }

    public static StringBuilder getGeneralOutput() {
        return generalOutput;
    }

    public static void clearGeneralOutput() {
        generalOutputMutex.lock();
        generalOutput.setLength(0);
        generalOutputMutex.unlock();
    }
}
