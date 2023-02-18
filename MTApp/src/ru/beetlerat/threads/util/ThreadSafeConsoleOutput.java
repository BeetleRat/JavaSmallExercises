package ru.beetlerat.threads.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeConsoleOutput {
    private static final Lock consoleMutex=new ReentrantLock();
    public static void consoleOutput(String string){
        consoleMutex.lock();
        System.out.print(string);
        consoleMutex.unlock();
    }

}
