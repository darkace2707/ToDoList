package com.company.BL;

import com.company.DB.TaskDB;

public class Mapper {

    public  static TaskBL  map(TaskDB task){
        return new TaskBL(task.getId(), task.getName(), task.getDeadlineDate(), task.getStatus(), task.getMasterID());
    }

    public  static TaskDB  map(TaskBL task){
        return new TaskDB(task.getId(), task.getName(), task.getDeadlineDate(), task.getStatus(), task.getMasterID());
    }

}
