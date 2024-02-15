package com.business.logic;

import com.GUI.View;
import com.model.Server;
import com.model.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;
    private View view;
    public Scheduler(int maxNoServers, int maxTasksPerServer, View view){
        this.view = view;
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer=maxTasksPerServer;
        servers = new ArrayList<Server>(maxNoServers);
        for(int i = 0 ; i < maxNoServers; i++){
            Server s = new Server(maxTasksPerServer,i, view);
            servers.add(s);
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ConcreteStrategyQueue();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task t){
        int index = strategy.addTask(servers,t);
        view.addIcon(index);
    }

    public void decrementServiceTime(){

        synchronized (this) {
            for (Server s : servers) {

                Task peekTask = s.getTasksQueue().peek();
                if (peekTask != null) {
                peekTask.decrementServiceTime();
                if (peekTask.getServiceTime() <= 0) {
                    s.getTasksQueue().poll();
                    view.removeIcon(s.getId());
                }
            }
            }
        }
    }

    public void decrementWaitingPeriod(){
        for(Server s : servers){
            if(s!=null){
                s.decrementWaitingPeriod();
            }
        }
    }

    public void incrementSpendingTimeForAll(){
        for(Server s : servers) {
            Iterator<Task> tasksIterator = s.getTasksQueue().iterator();
            while (tasksIterator.hasNext()) {
                Task t = tasksIterator.next();
                t.incrementSpendingTimeInQueue();
            }
        }
    }
    public int findPeakHour(){
        int countClients=0;
        for(Server s : servers){
            countClients+=s.getTaskQueue().size();
        }
        return countClients;
    }
    public String toString(){
        String result = new String("");
        for(int i = 0 ; i < maxNoServers; i++) {
            Server s = servers.get(i);
            int nbOfTasks = s.getTasksQueue().size();
            result += "Queue" + i + ":";
            if (nbOfTasks != 0) {
                result += s;
            } else {
                result += "Closed -- " + s.getWaitingPeriod();
            }
            result+="\n";
        }
        return result;
    }
    public List<Server> getServers(){
        return servers;
    }


}
