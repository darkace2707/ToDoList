package com.company.UI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {

    private SimpleDateFormat dateFormat;

    public DateParser(String dateFormat) {
        this.dateFormat = new SimpleDateFormat(dateFormat);
        //this.dateFormat =  new SimpleDateFormat("H:m, d MMMMM y");
    }

    public Date parsingDate(String date) {

        try {
            return dateFormat.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
