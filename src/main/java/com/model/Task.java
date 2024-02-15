package com.model;

public class Task implements Comparable {
    private static int generatorID = 0;
    private int ID;
    private int arrivalTime;
    private int serviceTime;
    private int timeSpentInQueue;


    public Task(int arrivalTime, int serviceTime) {
        generatorID++;
        this.ID = generatorID;
        this.arrivalTime = arrivalTime;
        this.ID = ID;
        this.serviceTime = serviceTime;
        timeSpentInQueue = 0;
    }


    public void setID(int ID) {
        this.ID = ID;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getID() {
        return ID;
    }

    public int getTimeSpentInQueue() {
        return timeSpentInQueue;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void decrementServiceTime() {
        if (this.serviceTime  > 0)
        {
            this.serviceTime--;
        }
    }

    public void incrementSpendingTimeInQueue() {
        this.timeSpentInQueue++;
    }

    public String toString() {
        return "( "+ID + ": A:" + arrivalTime + "; S:" + serviceTime + "; W:" + timeSpentInQueue + " ); ";
    }

    public int compareTo(Object t) {
        return this.arrivalTime - ((Task) t).arrivalTime;
    }

}
