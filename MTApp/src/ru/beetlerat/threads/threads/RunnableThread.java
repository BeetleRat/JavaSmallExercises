package ru.beetlerat.threads.threads;

import ru.beetlerat.threads.util.Calculations;
import ru.beetlerat.threads.util.ThreadSafeConsoleOutput;

import java.util.concurrent.Semaphore;

public abstract class RunnableThread implements Runnable {
    private Thread thread;
    private int timeInterval;

    public RunnableThread(String threadName, int timeInterval) {
        this.thread = new Thread(this, threadName);
        this.timeInterval = timeInterval;
        this.thread.start();
    }

    private void printNameToConsole() {
        ThreadSafeConsoleOutput.consoleOutput(this.thread.getName());
    }

    protected void performComplexCalculations() {
        for (int i = 0; i < timeInterval; i++) {
            ThreadSafeConsoleOutput.consoleOutput(thread.getName());
            Calculations.generalOutput.append(thread.getName());
            Calculations.run();
        }
    }

    protected void performComplexCalculations(Semaphore acquire, Semaphore release) throws InterruptedException {
        for (int i = 0; i < timeInterval; i++) {
            acquire.acquire(1);
            ThreadSafeConsoleOutput.consoleOutput(thread.getName());
            Calculations.generalOutput.append(thread.getName());
            Calculations.run();
            release.release();
        }
    }

    protected abstract void threadActions() throws InterruptedException;

    public Thread getThread() {
        return thread;
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
