package ru.beetlerat.threads.threads;

import ru.beetlerat.threads.util.Calculations;
import ru.beetlerat.threads.util.ThreadSafeConsoleOutput;

import java.util.concurrent.Semaphore;

public abstract class ThreadSuccessor extends Thread {
    private final int timeInterval;

    public ThreadSuccessor(String threadName, int timeInterval) {
        super(threadName);
        this.timeInterval = timeInterval;
        start();
    }

    private void printNameToConsole() {
        ThreadSafeConsoleOutput.consoleOutput(getThreadName());
        ThreadSafeConsoleOutput.appendToGeneralOutput(getThreadName());
    }

    protected void performComplexCalculations() {
        for (int i = 0; i < timeInterval; i++) {
            printNameToConsole();
            Calculations.performCalculations();
        }
    }

    protected void performComplexCalculations(Semaphore acquire, Semaphore release) throws InterruptedException {
        for (int i = 0; i < timeInterval; i++) {
            acquire.acquire(1);
            printNameToConsole();
            Calculations.performCalculations();
            release.release();
        }
    }

    protected abstract void threadActions() throws InterruptedException;

    public String getThreadName() {
        return getName();
    }

    @Override
    public void run() {
        try {
            threadActions();
        } catch (InterruptedException e) {
            ThreadSafeConsoleOutput.consoleOutput(e.toString());
        }
    }
}
