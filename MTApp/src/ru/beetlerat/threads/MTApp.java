package ru.beetlerat.threads;

import ru.beetlerat.threads.threadstarters.ExecutorThreadStarter;
import ru.beetlerat.threads.threadstarters.RunnableThreadStarter;
import ru.beetlerat.threads.threadstarters.SuccessorsThreadStarter;
import ru.beetlerat.threads.threadstarters.ThreadStarter;
import ru.beetlerat.threads.util.ThreadSafeConsoleOutput;

public class MTApp {
    public static void main(String[] args) {
        if(args.length>0){
            switch (args[0]) {
                case "Runnable":
                case "runnable":
                    new RunnableThreadStarter();
                    break;
                case "Successors":
                case "successors":
                    new SuccessorsThreadStarter();
                    break;
                case "Executor":
                case "executor":
                    new ExecutorThreadStarter();
                    break;
                default:
                    ThreadSafeConsoleOutput.consoleOutput("Wrong argument. Enter one of the arguments:\nRunnable\nSuccessors\nExecutor\n");
            }
        }else {
            ThreadSafeConsoleOutput.consoleOutput("Enter one of the arguments:\nRunnable\nSuccessors\nExecutor\n");
        }


        ThreadSafeConsoleOutput.consoleOutput("Main thread was ended.\n");
    }


}
