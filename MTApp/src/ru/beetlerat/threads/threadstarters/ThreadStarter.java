package ru.beetlerat.threads.threadstarters;

import ru.beetlerat.threads.threads.RunnableThread;
import ru.beetlerat.threads.threads.ThreadSuccessor;
import ru.beetlerat.threads.util.Calculations;
import ru.beetlerat.threads.util.ThreadSafeConsoleOutput;

import java.util.concurrent.Semaphore;

public abstract class ThreadStarter<T> {
    protected static final int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5, G = 6, H = 7, I = 8, K = 9, M = 10, K_PAR = 11, H_PAR = 12, I_PAR = 13;
    protected static final int TIME_INTERVAL = 3;
    // Интервалы с несинхронизированными потоками: bcdi
    // Интервалы с чередованием потоков: KHI
    private static final String THREAD_PATTERN = "abi\nbcdi\nbefhi\nbgfhi\nKHI\nm";

    public ThreadStarter() {
        Calculations.generalOutput.setLength(0);

        T[] threads = createThreads();

        waitOtherThreads(threads);

        if (Calculations.isStringMatchesPattern(Calculations.generalOutput.toString(), THREAD_PATTERN, TIME_INTERVAL)) {
            ThreadSafeConsoleOutput.consoleOutput("\nResult is correct\n");
        } else {
            ThreadSafeConsoleOutput.consoleOutput("\nResult is NOT correct\n");
        }
    }

    protected abstract void waitOtherThreads(T[] threads);

    protected abstract T[] createThreads();

    protected Semaphore[] createSemaphores() {
        Semaphore[] semaphores = new Semaphore[14];
        for (int i = 0; i < 14; i++) {
            semaphores[i] = new Semaphore(5);
            try {
                semaphores[i].acquire(5);
            } catch (InterruptedException e) {
                ThreadSafeConsoleOutput.consoleOutput(e.toString());
            }
        }
        return semaphores;
    }

    protected void acquireSemaphores(Semaphore[] semaphores, Integer... semaphoresName) throws InterruptedException {
        for (Integer semaphore : semaphoresName) {
            semaphores[semaphore].acquire(1);
        }
    }
}
