package com.company.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {

    private static Connection con = null;

    public static Connection getConnection() {
        //jdbc:mysql://localhost/db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC

        final String url = "jdbc:mysql://" + Configs.dbHost + ":" + Configs.dbPort + "/" + Configs.dbName+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        System.out.println(url);
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, Configs.dbUser, Configs.dbPass);
        } catch (SQLException e) {
            e.printStackTrace();
        }/* catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        return con;
    }

    //stmt = con.createStatement();

    /*public static void close() {
        try { con.close();  } catch(SQLException se) { se.printStackTrace(); }
    }*/

    public static void updateRepository(String query) {
        try {
            con.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //try { con.close();  } catch(SQLException se) { se.printStackTrace(); }
        }
    }
}
