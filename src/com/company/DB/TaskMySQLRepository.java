package com.company.DB;

import com.company.UI.DateParser;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class TaskMySQLRepository implements TaskRepository {

    private String dateFormat = "y-M-d H:m:s";

    private int userID;

    public TaskMySQLRepository(int userID) {
        this.userID = userID;
    }

    public TaskDB getByName(String name) {
        String query = String.format("SELECT * FROM ToDoList.Task\n" +
                                        "INNER JOIN TaskUsers ON id = TaskID\n" +
                                        "WHERE TaskName = \"%s\" && UserID = %d", name, userID);

        Connection con = MySQLConnection.getConnection();
        ResultSet rs = null;

        try {
            rs = con.createStatement().executeQuery(query);

            while(rs.next()) {
                DateParser dateParser = new DateParser(dateFormat);
                return Mapper.map(rs, dateParser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close();  } catch(SQLException se) { se.printStackTrace(); }
            try { con.close(); } catch(SQLException se) { se.printStackTrace(); }
        }
        return null;
    }

    public void update(TaskDB task, TaskDB newTask) {
        String query = String.format("UPDATE Task\n" +
                "INNER JOIN TaskUsers ON id = TaskID \n" +
                "SET TaskName = \"%1$s\", DeadlineDate = \"%2$tF %2$tT\", Status = \"%3$s\"\n" +
                "WHERE id = %4$d && UserID = %d5$\n", newTask.getName(), newTask.getDeadlineDate(), newTask.getStatus().toString(), task.getId(), userID);

        Connection con = MySQLConnection.getConnection();
        MySQLConnection.updateRepository(query);
    }

    public void remove(TaskDB task) {
        String query = String.format("DELETE T FROM Task T\n" +
                "INNER JOIN TaskUsers TU ON T.id = TU.TaskID\n" +
                "WHERE T.id = %d AND TU.UserID = %d;\n", task.getId(), userID);

        Connection con = MySQLConnection.getConnection();
        MySQLConnection.updateRepository(query);
    }

    public List<TaskDB> getTasks() {
        String query = String.format("SELECT * FROM Task INNER JOIN TaskUsers ON id = TaskID WHERE UserID = %d", userID);

        Connection con = MySQLConnection.getConnection();
        ResultSet rs = null;

        try {
            rs = con.createStatement().executeQuery(query);

            List<TaskDB> tasks = new ArrayList<>();

            while(rs.next()) {
                DateParser dateParser = new DateParser(dateFormat);
                tasks.add(Mapper.map(rs, dateParser));
            }
            return tasks;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close();  } catch(SQLException se) { se.printStackTrace(); }
            try { con.close(); } catch(SQLException se) { se.printStackTrace(); }
        }
        return null;
    }

    public void add(TaskDB task) {

        Connection con = MySQLConnection.getConnection();
        ResultSet rs = null;

        int taskID = 0;
        try {
            con.setAutoCommit(false);

            String query = String.format("INSERT INTO ToDoList.Task (TaskName, DeadlineDate, Status, TaskMasterID) VALUES (\"%1$s\", \"%2$tF %2$tT\", \"%3$s\", %4$d)",
                    task.getName(), task.getDeadlineDate(), task.getStatus(), userID);
            con.createStatement().executeUpdate(query);

            query = String.format("SELECT id FROM ToDoList.Task\n" +
                    "WHERE TaskName = \"%s\" && TaskMasterID = %d", task.getName(), userID);

            rs = con.createStatement().executeQuery(query);

            while (rs.next()) {
                taskID = rs.getInt("id");
            }

            query = String.format("INSERT INTO ToDoList.TaskUsers (TaskID, UserID) VALUES (%d ,%d)", taskID, userID);
            con.createStatement().executeUpdate(query);

            con.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {con.rollback();} catch (SQLException se) { se.printStackTrace();}
        } finally {
            try { rs.close();  } catch(SQLException se) { se.printStackTrace(); }
            try { con.close(); } catch(SQLException se) { se.printStackTrace(); }
        }
    }
}
