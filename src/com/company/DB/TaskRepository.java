package com.company.DB;

import java.util.List;

public interface TaskRepository {

    TaskDB getByName(String name);

    void update(TaskDB task, TaskDB newTask);

    void remove(TaskDB task);

    List<TaskDB> getTasks();

    void add(TaskDB task);

    String getInvolvedUsers(int taskID);

    void updateInvolvedUsers(int taskID, List<String> users);

    int getUserID();
}

//public interface Repository<T>{
//    void create(T entity);
//    //....
//    List<T> query(//request)
//}
//
//public class TaskRepository implements Repository<DBTask>{
//
//}
