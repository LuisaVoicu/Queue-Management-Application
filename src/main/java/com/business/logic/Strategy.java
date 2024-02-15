package com.business.logic;

import com.model.Server;
import com.model.Task;
import java.util.List;

public interface Strategy {
    public int addTask(List<Server> servers, Task t);
}


