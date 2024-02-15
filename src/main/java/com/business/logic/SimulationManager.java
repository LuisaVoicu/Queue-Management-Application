package com.business.logic;

import com.GUI.Controller;
import com.GUI.View;
import com.model.Server;
import com.model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimulationManager implements Runnable {

    private final static int MAX_LENGTH = 1000000;

    //valori default -->  in cazul in care utilizatorul nu va introduce nimic
    private int maxSimulation = 20;
    private int nbOfClients = 4;
    private int maxNoServers = 3; // nb of queues
    private int maxTasksPerServer = 20;
    private int maxArrival = 6;
    private int minArrival = 2;
    private int maxService = 4;
    private int minService = 2;
    private String queueOutput;


    private float averageWaitingTimeTotal = 0;
    private float averageServiceTime = 0;
    private int peakHour = 0;
    private Thread t;

    private SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME; //tot utilizatoru alege
    private Scheduler scheduler;
    private List<Task> generatedTasks;
    private View view;
    private FileWriter file;

    public SimulationManager(View view) {
        this.view = view;
    }

    public void initScheduler() {
        scheduler = new Scheduler(maxNoServers, maxTasksPerServer, view);
        scheduler.changeStrategy(selectionPolicy);
        generateNRandomTasks();
    }

    private void generateNRandomTasks() {
        Random rand = new Random();
        generatedTasks = new ArrayList<Task>();

        for (int i = 0; i < nbOfClients; i++) {
            int randService = rand.nextInt(maxService - minService) + minService;
            int randArrival = rand.nextInt(maxArrival - minArrival) + minArrival;
            Task t = new Task(randArrival, randService);
            averageServiceTime += randService;
            generatedTasks.add(t);
        }
        averageServiceTime = (float) 1.0 * averageServiceTime / nbOfClients;
        Collections.sort(generatedTasks);
    }

    private void searchForCurrentTime(int currentTime) {

        ArrayList<Integer> toBeDeleted = new ArrayList<Integer>();
        Integer index = -1;
        for (Task task : generatedTasks) {
            index++;
            if (task.getArrivalTime() == currentTime) {
                toBeDeleted.add(index);
                int r = index;
            }
        }
        //sortam in ordine descrescatoare indecsii pentru a nu schimba valoarea lor in array-ul initial
        Collections.sort(toBeDeleted, Collections.reverseOrder());
        for (Integer i : toBeDeleted) {
            int r = i; // conversie la primitiva, altel va face remove(Object)
            scheduler.dispatchTask(generatedTasks.get(r));
            generatedTasks.remove(r);
            //todo update ui frame
        }
    }


    public static void writeToFile(String filePath, String message) {
        try (FileWriter fw = new FileWriter(filePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        int currentTime = 0;

        while (currentTime < maxSimulation) {
            synchronized (this) {
                System.out.println("\n-----------------------\nCurrent Time: " + currentTime);
                queueOutput += "\n-----------------------\nCurrent Time: " + currentTime + "\n";
                searchForCurrentTime(currentTime);

                showList();
                scheduler.decrementServiceTime();
                scheduler.incrementSpendingTimeForAll();
                scheduler.decrementWaitingPeriod();

                int x = scheduler.findPeakHour();
                //System.out.println(currentTime + " " + x);
                if (x > peakHour) {
                    peakHour = x;
                }
                currentTime++;
                try {
                    wait(3000);
                } catch (InterruptedException e) {
                    //throw new RuntimeException(e);
                }

            }
        }
        averageWaitingTimeTotal = (float) (1.0 * Server.getWaitingTimeAverageAsInt() / nbOfClients);
        System.out.println("Average Waiting Time: " + averageWaitingTimeTotal);
        System.out.println("Average Service Time: " + averageServiceTime);
        System.out.println("Peak Hour: " + peakHour);

        queueOutput += "\nAverage Waiting Time: " + averageWaitingTimeTotal;
        queueOutput += "\nAverage Service Time: " + averageServiceTime;
        queueOutput += "\nPeak Hour: " + peakHour;
        showFinalResults();

    }

    private void showList() {
        System.out.println("Waiting List: ");
        queueOutput += "Waiting List: ";
        for (Task task : generatedTasks) {
            System.out.print(task + "  ");
            queueOutput += task + " ";
        }
        System.out.println("\n");
        queueOutput += "\n-----------------------\n";
        System.out.println(scheduler);

        queueOutput += scheduler.toString();
        int test = 0;
        if (test == 1) {
            writeToFile("D:\\an2sem2\\TP\\Assign2_queue_management\\src\\main\\java\\com\\results\\t1.txt", queueOutput);
        } else if (test == 2) {
            writeToFile("D:\\an2sem2\\TP\\Assign2_queue_management\\src\\main\\java\\com\\results\\t2.txt", queueOutput);
        } else if (test == 3) {
            writeToFile("D:\\an2sem2\\TP\\Assign2_queue_management\\src\\main\\java\\com\\results\\t3.txt", queueOutput);
        }
        view.setAreaText(queueOutput);
    }

    private void showFinalResults() {
        view.setAreaText(queueOutput);
        int test = 0;
        if (test == 1) {
            writeToFile("D:\\an2sem2\\TP\\Assign2_queue_management\\src\\main\\java\\com\\results\\t1.txt", queueOutput);
        } else if (test == 2) {
            writeToFile("D:\\an2sem2\\TP\\Assign2_queue_management\\src\\main\\java\\com\\results\\t2.txt", queueOutput);
        } else if (test == 3) {
            writeToFile("D:\\an2sem2\\TP\\Assign2_queue_management\\src\\main\\java\\com\\results\\t3.txt", queueOutput);
        }
        view.setAreaText(queueOutput);
    }

    public void startThread() {
        t = new Thread(this);
        t.start();
    }

    public void stopThread() {
        if (t != null) {
            t.interrupt();
        }
    }

    public static void main(String[] args) {
        View v = new View();
        SimulationManager gen = new SimulationManager(v);
        Controller c = new Controller(v, gen);
    }

    public void setMaxSimulation(int maxSimulation) {
        this.maxSimulation = maxSimulation;
    }

    public void setNbOfClients(int nbOfClients) {
        this.nbOfClients = nbOfClients;
    }

    public void setMinService(int minService) {
        this.minService = minService;
    }

    public void setMaxService(int maxService) {
        this.maxService = maxService;
    }

    public void setMinArrival(int minArrival) {
        this.minArrival = minArrival;
    }

    public void setMaxArrival(int maxArrival) {
        this.maxArrival = maxArrival;
    }

    public void setMaxTasksPerServer(int maxTasksPerServer) {
        this.maxTasksPerServer = maxTasksPerServer;
    }

    public void setMaxNoServers(int maxNoServers) {

        this.maxNoServers = maxNoServers;
    }

    public int getMaxNoServers() {
        return maxNoServers;
    }

    public int getNbOfClients() {
        return nbOfClients;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public String getQueueOutput() {
        return queueOutput;
    }

    public void setQueueOutput(String s) {
        queueOutput = s;
    }

    public void setSelectionPolicy(SelectionPolicy selectionPolicy) {
        this.selectionPolicy = selectionPolicy;
    }
}
