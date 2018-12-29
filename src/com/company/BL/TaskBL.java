package com.company.BL;

import com.company.UI.Status;

import java.util.Date;
import java.util.Objects;


public class TaskBL {

    private int id;

    private String name;

    private Status status;

    private Date deadlineDate = new Date();

    private int masterID;

    public TaskBL(int id, String name, Date deadlineDate, Status status, int masterID) {
        this.id = id;
        this.name = name;
        this.deadlineDate = deadlineDate;
        this.status = status;
        this.masterID = masterID;
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

    public int getMasterID() {
        return masterID;
    }

    @Override
    public String toString() {
        return String.format("%1$s%2$s %3$tR, %3$td %3$tB %3$tY", name, ":", deadlineDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskBL)) return false;
        TaskBL task = (TaskBL) o;
        return Objects.equals(getName(), task.getName()) &&
                getStatus() == task.getStatus() &&
                Objects.equals(getDeadlineDate(), task.getDeadlineDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getStatus(), getDeadlineDate());
    }
}
