package ru.beetlerat.threads;

import ru.beetlerat.threads.threadstarters.RunnableThreadStarter;
import ru.beetlerat.threads.threadstarters.SuccessorsThreadStarter;
import ru.beetlerat.threads.threadstarters.ThreadStarter;
import ru.beetlerat.threads.util.ThreadSafeConsoleOutput;

public class MTApp {
    public static void main(String[] args) {
        switch (args[0]){
            case "Runnable":
            case "runnable":
                new RunnableThreadStarter();
                break;
            case "Successors":
            case "successors":
                new SuccessorsThreadStarter();
                break;
            default:
                new RunnableThreadStarter();
        }

        ThreadSafeConsoleOutput.consoleOutput("Main thread was ended.\n");
    }


}
