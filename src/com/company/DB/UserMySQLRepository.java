package com.company.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMySQLRepository implements UserRepository {

    private final String tableName = "Users";

    public int logIn(String login, String password) {
        String query = String.format("SELECT UserID FROM %s WHERE Login = \"%s\" && Password = \"%s\"", tableName, login, password);

        ResultSet rs = null;
        Connection con = MySQLConnection.getConnection();

        try {

            rs = con.createStatement().executeQuery(query);

            rs.next();
            return rs.getInt("UserID");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch(SQLException se) { se.printStackTrace(); }
            try { con.close();  } catch(SQLException se) { se.printStackTrace(); }
        }
        return -1;
    }

    public void signUp(String login, String password) {
        String query = String.format("INSERT INTO Users (Login, Password) VALUES (\"%s\", \"%s\")", login, password);
        MySQLConnection.updateRepository(query);
    }
}
