package com.company.BL;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface TaskManager {

    TaskBL getByName(String name);

    void update(TaskBL task, TaskBL newTask);

    void add(TaskBL task);

    void remove(TaskBL task);

    List<TaskBL> getInIterval(Date startDate, Date endDate);

    List<TaskBL> getTasks();

    String getInvolvedUsers(int taskID);
}
