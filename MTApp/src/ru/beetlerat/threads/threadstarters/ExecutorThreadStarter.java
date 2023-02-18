package ru.beetlerat.threads.threadstarters;

import ru.beetlerat.threads.threads.CallableThread;
import ru.beetlerat.threads.util.ThreadSafeConsoleOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorThreadStarter extends ThreadStarter<CallableThread> {
    private ExecutorService threadsService;
    private List<Future<Integer>> tasks;

    private void startThreads(CallableThread[] threads) {
        this.tasks = new ArrayList<>();
        this.threadsService = Executors.newFixedThreadPool(11);
        for (int i = threads.length - 1; i >= 0; i--) {
            Future<Integer> task = threadsService.submit(threads[i]);
            tasks.add(task);
        }
    }

    @Override
    protected void waitOtherThreads(CallableThread[] threads) {
        try {
            for (int i = 0; i < 11; i++) {
                tasks.get(i).get();
            }
            threadsService.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            ThreadSafeConsoleOutput.consoleOutput(String.format("\nThread exception: %s\n", e));
            threadsService.shutdown();
        }
    }

    @Override
    protected CallableThread[] createThreads() {
        Semaphore[] semaphores = createSemaphores();

        CallableThread[] threads = new CallableThread[11];
        threads[M] = new CallableThread("m", TIME_INTERVAL) {
            @Override
            protected void threadActions() throws InterruptedException {
                acquireSemaphores(semaphores, K, H, I);
                performComplexCalculations();
            }
        };
        threads[K] = new CallableThread("k", TIME_INTERVAL) {
            @Override
            protected void threadActions() throws InterruptedException {
                acquireSemaphores(semaphores, G, B, F, H, I);
                semaphores[I_PAR].release();
                performComplexCalculations(semaphores[I_PAR], semaphores[K_PAR]);
                semaphores[K].release(1);
            }
        };
        threads[I] = new CallableThread("i", TIME_INTERVAL) {
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
        threads[H] = new CallableThread("h", TIME_INTERVAL) {
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
        threads[G] = new CallableThread("g", TIME_INTERVAL) {
            @Override
            protected void threadActions() throws InterruptedException {
                acquireSemaphores(semaphores, E, F, H, B, I);
                performComplexCalculations();
                semaphores[G].release(3);
            }
        };
        threads[F] = new CallableThread("f", TIME_INTERVAL) {
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
        threads[E] = new CallableThread("e", TIME_INTERVAL) {
            @Override
            protected void threadActions() throws InterruptedException {
                acquireSemaphores(semaphores, C, D, B, I);
                performComplexCalculations();
                semaphores[E].release(5);
            }
        };
        threads[D] = new CallableThread("d", TIME_INTERVAL) {
            @Override
            protected void threadActions() throws InterruptedException {
                acquireSemaphores(semaphores, A, B, I);
                performComplexCalculations();
                semaphores[D].release(5);
            }
        };
        threads[C] = new CallableThread("c", TIME_INTERVAL) {
            @Override
            protected void threadActions() throws InterruptedException {
                acquireSemaphores(semaphores, A, B, I);
                performComplexCalculations();
                semaphores[C].release(5);
            }
        };
        threads[B] = new CallableThread("b", TIME_INTERVAL) {
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
        threads[A] = new CallableThread("a", TIME_INTERVAL) {
            @Override
            protected void threadActions() throws InterruptedException {

                performComplexCalculations();
                semaphores[A].release(4);
            }
        };

        startThreads(threads);

        return threads;
    }
}
