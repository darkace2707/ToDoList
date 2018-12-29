package com.company.DB;


import com.company.UI.DateParser;
import com.company.UI.Status;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Mapper {

public static TaskDB  map(ResultSet rs, DateParser dateParser) throws SQLException {
        return new TaskDB(rs.getInt("id"), rs.getString("TaskName"), dateParser.parsingDate(rs.getString("DeadlineDate")), Status.valueOf(rs.getString("Status")), rs.getInt("TaskMasterID"));
    }
}
