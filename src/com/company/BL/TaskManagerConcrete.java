package com.company.BL;

import com.company.DB.TaskDB;
import com.company.DB.TaskFileRepository;
import com.company.DB.TaskRepository;
import com.company.obs.ProgressObservable;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TaskManagerConcrete extends ProgressObservable implements TaskManager {

    private final TaskRepository taskRepository;

    public TaskManagerConcrete(TaskRepository tRepo)  {
        this.taskRepository = tRepo;
    }

    public TaskBL getByName(String name) {
        return Mapper.map(taskRepository.getByName(name));
    }

    public void update(TaskBL task, TaskBL newTask) {
        taskRepository.update(Mapper.map(task), Mapper.map(newTask));
    }

    public void add(TaskBL task) {
        taskRepository.add(Mapper.map(task));
    }

    public void remove(TaskBL task) {
        taskRepository.remove(Mapper.map(task));
    }

    public List<TaskBL> getInIterval(Date startDate, Date endDate) {
        List<TaskBL> tasks = new ArrayList<>();

        for (TaskDB task : taskRepository.getTasks()) {
            if (task.getDeadlineDate().after(startDate) && task.getDeadlineDate().before(endDate)) {
                tasks.add(Mapper.map(task));
            }
        }

        return Collections.unmodifiableList(tasks);
    }

    public List<TaskBL> getTasks() {
        List<TaskBL> tasks = new ArrayList<>();
        List<TaskDB> repoTasks = new ArrayList<>(taskRepository.getTasks());
        int count = repoTasks.size();

        for (TaskDB task : repoTasks) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            tasks.add(Mapper.map(task));

            setProgress((getProgress() + 100 / count) % 101);
            notifyListeners();
        }

        setProgress(100);
        notifyListeners();

        return Collections.unmodifiableList(tasks);
    }

    public String getInvolvedUsers(int taskID) {
        return taskRepository.getInvolvedUsers(taskID);
    }

    @Override
    public void updateInvolvedUsers(int taskID, List<String> users) {
        taskRepository.updateInvolvedUsers(taskID, users);
    }

    @Override
    public int getUserID() {
        return taskRepository.getUserID();
    }
}
