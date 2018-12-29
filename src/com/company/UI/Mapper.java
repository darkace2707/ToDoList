package com.company.UI;

import com.company.BL.TaskBL;


public class Mapper {
    public static TaskBL map(TaskUI task) {
        return new TaskBL(task.getId(), task.getName(), task.getDeadlineDate(), task.getStatus(), task.getMasterID());
    }

}
