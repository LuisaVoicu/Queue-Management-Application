package com.model;

import com.GUI.View;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private static AtomicInteger waitingTimeAverage;
    private int id;
    private View view;
    private int maxTasksPerServer = 100; //todo in functie de ce setez capacitatea?

    public Server(int maxTasksPerServer, int id, View view) {
        this.view = view;
        this.maxTasksPerServer = maxTasksPerServer;
        this.id = id;
        tasks = new LinkedBlockingQueue<Task>(maxTasksPerServer);
        waitingPeriod = new AtomicInteger(0);
        waitingTimeAverage = new AtomicInteger(0);

    }

    public void addTask(Task newTask) {

        Thread threadServer = new Thread(this);
        threadServer.start();
        tasks.add(newTask);

        int wp = waitingPeriod.get();
        int at = newTask.getArrivalTime();
        wp = wp + newTask.getServiceTime();
        waitingPeriod.set(wp);

        int wta = waitingTimeAverage.get();
        waitingTimeAverage.set(wta + wp);
    }

    public Task popTask() {
        Task t = tasks.peek();
        if (t == null) {
            return null;
        }
        if (t.getServiceTime() <= 0) {
            // tasks.poll();
            //  view.removeIcon(id);
        }
        return t;
    }

    public void run() {

        while (!tasks.isEmpty()) {
            //  System.out.println("hello i'm thread  " + name);
            synchronized (this) {
                popTask(); // ca nu cumva 2 thread-uri sa execute in acelasi timp metoda; ex: 2 clienti ies in acelasi timp din aceeasi coada
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    public BlockingQueue<Task> getTaskQueue() {
        return tasks;
    }

    public Task[] getTasks() {
        Task[] taskArray = new Task[maxTasksPerServer];
        int index = 0;
        return taskArray;
    }

    public int getId() {
        return id;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public BlockingQueue<Task> getTasksQueue() {
        return tasks;
    }

    public static int getWaitingTimeAverageAsInt() {
        int result = waitingTimeAverage.get();
        ;
        return result;
    }

    public void decrementWaitingPeriod() {
        int wp = waitingPeriod.get();
        if (wp != 0) {
            waitingPeriod.set(wp - 1);
        }
    }

    public String toString() {
        return "Waiting period: " + waitingPeriod + "\n" + tasks.toString() + "\n";
    }
}
