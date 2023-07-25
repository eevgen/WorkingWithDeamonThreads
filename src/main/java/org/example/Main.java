package org.example;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.*;
import static java.util.concurrent.TimeUnit.*;

public class Main {

    private static final String MESSAGE_FOR_RUNNING_THREAD = "Using Google";
    private static final String NAME_OF_THE_STOPPING_THREAD = "Stopping Thread";

    public static void main(String[] args) {

        Thread runningThread = new Thread(() -> {
            try {
                while (!currentThread().isInterrupted()) {
                    System.out.println(MESSAGE_FOR_RUNNING_THREAD);
                    MILLISECONDS.sleep(500);
                }
            } catch (InterruptedException ex) {
                currentThread().interrupt();
            }
        });

        Thread cashUploadingThreadDeamon = new Thread(new Task());

        Thread stoppingThread = new Thread(() -> {
            try {
                SECONDS.sleep(5);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            } finally {
                System.out.println(currentThread().getName() + " has been stopped all Threads");
                runningThread.interrupt();
            }
        });

        runningThread.start();
        stoppingThread.start();
        stoppingThread.setName(NAME_OF_THE_STOPPING_THREAD);
        cashUploadingThreadDeamon.setDaemon(true);
        cashUploadingThreadDeamon.start();

    }

    private static final class Task implements Runnable{
        @Override
        public void run() {
            while (true) {
                System.out.println("Uploading information into the Cash ");
                try {
                    SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    currentThread().interrupt();
                }
            }
        }
    }
}