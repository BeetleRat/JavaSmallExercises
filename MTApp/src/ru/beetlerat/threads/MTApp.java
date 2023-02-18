package ru.beetlerat.threads;

import ru.beetlerat.threads.threads.RunnableThread;
import ru.beetlerat.threads.util.Calculations;
import ru.beetlerat.threads.util.ThreadSafeConsoleOutput;

import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Semaphore;

public class MTApp {
    private static final int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5, G = 6, H = 7, I = 8, K = 9, M = 10, K_PAR = 11, H_PAR = 12, I_PAR = 13;
    private static final int TIME_INTERVAL = 3;

    // Интервалы с несинхронизированными потоками: bcdi
    //
    //Интервалы с чередованием потоков: khi
    public static void main(String[] args) {
        Calculations.generalOutput.setLength(0);
        RunnableThread[] threads = createThreads();
        waitOtherThreads(threads);
        System.out.println();
        System.out.println(Calculations.isStringMatchesPattern(Calculations.generalOutput.toString(), "abi\nbcdi\nbefhi\nbgfhi\nKHI\nm", TIME_INTERVAL));
        ThreadSafeConsoleOutput.consoleOutput("Main thread was ended.\n");
    }

    private static void waitOtherThreads(RunnableThread[] threads) {
        try {
            for (int i = 0; i < 11; i++) {
                threads[i].getThread().join();
            }
        } catch (InterruptedException e) {
            ThreadSafeConsoleOutput.consoleOutput(String.format("\nThread exception: %s\n", e));
        }
    }

    private static RunnableThread[] createThreads() {
        Semaphore[] semaphores = createSemaphores();

        RunnableThread[] threads = new RunnableThread[11];
        threads[M] = new RunnableThread("m", TIME_INTERVAL) {
            @Override
            protected void threadActions() throws InterruptedException {
                acquireSemaphores(semaphores, K, H, I);
                performComplexCalculations();
            }
        };
        threads[K] = new RunnableThread("k", TIME_INTERVAL) {
            @Override
            protected void threadActions() throws InterruptedException {
                acquireSemaphores(semaphores, G, B, F, H, I);
                semaphores[I_PAR].release();
                performComplexCalculations(semaphores[I_PAR], semaphores[K_PAR]);
                semaphores[K].release(1);
            }
        };
        threads[I] = new RunnableThread("i", TIME_INTERVAL) {
            @Override
            protected void threadActions() throws InterruptedException {
                performComplexCalculations();
                semaphores[I].release(3);

                acquireSemaphores(semaphores, A, B);
                performComplexCalculations();
                semaphores[I].release(4);

                acquireSemaphores(semaphores, C, D, B);
                performComplexCalculations();
                semaphores[I].release(4);

                acquireSemaphores(semaphores, E, F, H, B);
                performComplexCalculations();
                semaphores[I].release(2);

                acquireSemaphores(semaphores, G, F, B, H);
                performComplexCalculations(semaphores[H_PAR], semaphores[I_PAR]);
                semaphores[I].release(1);
            }
        };
        threads[H] = new RunnableThread("h", TIME_INTERVAL) {
            @Override
            protected void threadActions() throws InterruptedException {
                acquireSemaphores(semaphores, D, C, B, I);
                performComplexCalculations();
                semaphores[H].release(4);

                acquireSemaphores(semaphores, E, B, I, F);
                performComplexCalculations();
                semaphores[H].release(2);

                acquireSemaphores(semaphores, G, B, F, I);
                performComplexCalculations(semaphores[K_PAR], semaphores[H_PAR]);
                semaphores[H].release(1);
            }
        };
        threads[G] = new RunnableThread("g", TIME_INTERVAL) {
            @Override
            protected void threadActions() throws InterruptedException {
                acquireSemaphores(semaphores, E, F, H, B, I);
                performComplexCalculations();
                semaphores[G].release(3);
            }
        };
        threads[F] = new RunnableThread("f", TIME_INTERVAL) {
            @Override
            protected void threadActions() throws InterruptedException {
                acquireSemaphores(semaphores, D, C, B, I);
                performComplexCalculations();
                semaphores[F].release(4);

                acquireSemaphores(semaphores, E, B, I, H);
                performComplexCalculations();
                semaphores[F].release(3);
            }
        };
        threads[E] = new RunnableThread("e", TIME_INTERVAL) {
            @Override
            protected void threadActions() throws InterruptedException {
                acquireSemaphores(semaphores, C, D, B, I);
                performComplexCalculations();
                semaphores[E].release(5);
            }
        };
        threads[D] = new RunnableThread("d", TIME_INTERVAL) {
            @Override
            protected void threadActions() throws InterruptedException {
                acquireSemaphores(semaphores, A, B, I);
                performComplexCalculations();
                semaphores[D].release(5);
            }
        };
        threads[C] = new RunnableThread("c", TIME_INTERVAL) {
            @Override
            protected void threadActions() throws InterruptedException {
                acquireSemaphores(semaphores, A, B, I);
                performComplexCalculations();
                semaphores[C].release(5);
            }
        };
        threads[B] = new RunnableThread("b", TIME_INTERVAL) {
            @Override
            protected void threadActions() throws InterruptedException {

                performComplexCalculations();
                semaphores[B].release(3);

                acquireSemaphores(semaphores, A, I);
                performComplexCalculations();
                semaphores[B].release(4);

                acquireSemaphores(semaphores, C, D, I);
                performComplexCalculations();
                semaphores[B].release(4);

                acquireSemaphores(semaphores, E, F, H, I);
                performComplexCalculations();
                semaphores[B].release(3);
            }
        };

        threads[A] = new RunnableThread("a", TIME_INTERVAL) {
            @Override
            protected void threadActions() throws InterruptedException {

                performComplexCalculations();
                semaphores[A].release(4);
            }
        };
        return threads;
    }

    private static Semaphore[] createSemaphores() {
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

    private static void acquireSemaphores(Semaphore[] semaphores, Integer... semaphoresName) throws InterruptedException {
        for (Integer semaphore : semaphoresName) {
            semaphores[semaphore].acquire(1);
        }
    }
}
