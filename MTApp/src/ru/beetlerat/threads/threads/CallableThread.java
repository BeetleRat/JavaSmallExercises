package ru.beetlerat.threads.threads;

import ru.beetlerat.threads.util.Calculations;
import ru.beetlerat.threads.util.ThreadSafeConsoleOutput;

import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

public abstract class CallableThread implements Callable<Integer> {
    private final int timeInterval;
    private final String threadName;

    public CallableThread(String threadName, int timeInterval) {
        this.timeInterval = timeInterval;
        this.threadName = threadName;
    }

    public String getThreadName() {
        return threadName;
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

    @Override
    public Integer call() throws Exception {
        try {
            threadActions();
        } catch (InterruptedException e) {
            ThreadSafeConsoleOutput.consoleOutput(e.toString());
            return 0;
        }
        return 1;
    }
}
