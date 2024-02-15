package com.business.logic;

import com.model.Server;
import com.model.Task;
import java.util.List;

public class ConcreteStrategyTime implements Strategy{

    public int addTask(List<Server> servers, Task t) {
        int sMinServerIndex = getMinWaitingTimeServerIndex(servers);
        servers.get(sMinServerIndex).addTask(t);
        return sMinServerIndex;
    }

    private int getMinWaitingTimeServerIndex(List<Server> servers){
        int minWaiting = servers.get(0).getWaitingPeriod().get();
        int index=0;
        int minIndex=0;

        for(Server s : servers){
            int serverWaiting = s.getWaitingPeriod().get();
            if(minWaiting > serverWaiting){
                minWaiting = serverWaiting;
                minIndex=index;
            }
            index++;
        }

        return minIndex;
    }
}
