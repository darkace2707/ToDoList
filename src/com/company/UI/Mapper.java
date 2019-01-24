package com.company.UI;

import com.company.BL.TaskBL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Mapper {
    public static TaskBL map(TaskUI task) {
        return new TaskBL(task.getId(), task.getName(), task.getDeadlineDate(), task.getStatus(), task.getMasterID());
    }

    public static TaskUI map(TaskBL task) {
        return new TaskUI(task.getId(), task.getName(), task.getDeadlineDate(), task.getStatus(), task.getMasterID());
    }

    public static List<Integer> map(Date date) {
        List<Integer> values = new ArrayList<>();

        values.add(Integer.parseInt(String.format("%1$tY", date)));
        values.add(Integer.parseInt(String.format("%1$tm", date)));
        values.add(Integer.parseInt(String.format("%1$td", date)));

        return values;
    }
}
