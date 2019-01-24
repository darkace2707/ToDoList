package com.company.DB;

import com.company.UI.DateParser;
import com.company.UI.Status;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class TaskFileRepository implements TaskRepository {

    List<TaskDB> tasks;
    private String dateFormat = "H:m, d MMMMM y";
    private DateParser dateParser = new DateParser(dateFormat);
    private String fileName;

    @Override
    public String getInvolvedUsers(int taskID) {
        return null;
    }

    public TaskFileRepository(String fileName) {
        try {
            this.fileName = fileName;
            FileReader fileIn = new FileReader(fileName);

            tasks = new ArrayList<>();

            int id = 0;
            int c;
            String taskName = "";
            String date = "";
            String status = "";
            boolean nameFlag = true;
            boolean dateFlag = false;
            boolean statusFlag = false;

            while ((c = fileIn.read()) != -1) {

                if ((char)c == '\n') {
                    nameFlag = true;
                    dateFlag = false;
                    statusFlag = false;
                    status = status.substring(1);
                    //date = date.substring(1);
                    //System.out.println(taskName);
                    //System.out.println(date);
                    tasks.add(new TaskDB(id, taskName, dateParser.parsingDate(date), Status.valueOf(status), 1707));
                    taskName = "";
                    date = "";
                    status = "";
                    id++;
                    continue;
                }

                if ((char)c == ':' && nameFlag) {
                    nameFlag = false;
                    dateFlag = true;
                    statusFlag = false;
                    continue;
                }

                if ((char)c == '|') {
                    nameFlag = false;
                    dateFlag = false;
                    statusFlag = true;
                    date = date.substring(1, date.length() - 1);
                    continue;
                }

                if (nameFlag) {
                    taskName = taskName + (char)c;
                }

                if (dateFlag) {
                    date = date + (char)c;
                }

                if (statusFlag) {
                    status = status + (char)c;
                }

            }

            status = status.substring(1);
            tasks.add(new TaskDB(id, taskName, dateParser.parsingDate(date), Status.valueOf(status), 1707));

            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TaskDB getByName(String name) {
        for (TaskDB task : tasks) {
            if (task.getName().equals(name)) {
                return task;
            }
        }
        return null;
    }

    public void update(TaskDB task, TaskDB newTask) {
        for (TaskDB _task : tasks) {
            if (task.equals(_task)) {
                tasks.remove(_task);
                tasks.add(newTask);
                this.updateRepository();
                break;
            }
        }
    }

    public void remove(TaskDB task) {
        tasks.removeIf(_task -> _task.equals(task));
        this.updateRepository();
    }

    public List<TaskDB> getTasks() {
        return tasks.subList(0, tasks.size());
    }

    public void add(TaskDB task) {
        tasks.add(task);
        this.updateRepository();
    }

    private void updateRepository() {
        try {
            FileWriter fileOut = new FileWriter(fileName);
            for (TaskDB task : tasks) {
                if (task.equals(tasks.get(tasks.size() - 1))) {
                    fileOut.write(task.toString());
                } else {
                    fileOut.write(task.toString());
                    fileOut.write("\n");
                }
            }
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateInvolvedUsers(int taskID, List<String> users) {

    }

    @Override
    public int getUserID() {
        return 0;
    }
}
