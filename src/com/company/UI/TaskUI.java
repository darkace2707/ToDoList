package com.company.UI;

import com.company.BL.TaskManager;
import com.company.BL.TaskManagerConcrete;
import com.company.DB.TaskMySQLRepository;

import java.util.*;

public class TaskUI {

    private int id;

    private String name;

    private Status status;

    private Date deadlineDate = new Date();

    private String users;

    private int masterID;

    public TaskUI(int id, String name, Date deadlineDate, Status status, int masterID) {
        this.id = id;
        this.name = name;
        this.deadlineDate = deadlineDate;
        this.status = status;
        this.masterID = masterID;
        TaskManager tdl = new TaskManagerConcrete(new TaskMySQLRepository(masterID));
        this.users = tdl.getInvolvedUsers(id);
    }

    public int getMasterID() {
        return masterID;
    }

    public String getName() {
        return name;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return String.format("%1$s%2$s %3$tR, %3$td %3$tB %3$tY", name, ":", deadlineDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskUI)) return false;
        TaskUI task = (TaskUI) o;
        return Objects.equals(getName(), task.getName()) &&
                getStatus() == task.getStatus() &&
                Objects.equals(getDeadlineDate(), task.getDeadlineDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getStatus(), getDeadlineDate());
    }
}
