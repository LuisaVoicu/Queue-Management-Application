package com.business.logic;

import com.model.Server;
import com.model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy{
    public int addTask(List<Server> servers, Task t) {
        int shortestQueueIndex = getShortestQueueServerIndex(servers);
        servers.get(shortestQueueIndex).addTask(t);
        return shortestQueueIndex;
    }

    public int getShortestQueueServerIndex(List<Server> servers){

        int minQueue=servers.get(0).getTasksQueue().size();
        int indexMin=0;
        int index=0;
        for(Server s: servers){
            int nbOfTasks = s.getTasksQueue().size();
            if(nbOfTasks < minQueue){
                minQueue = nbOfTasks;
                indexMin = index;
            }
            index++;
        }
        return indexMin;
    }
}
