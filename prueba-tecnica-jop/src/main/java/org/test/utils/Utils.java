package org.test.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static Date convertStringToDateYYMMDD(String birthdate) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(birthdate);
    }

    public static boolean isValidDate(String startDate, String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(startDate);
        Date date2 = sdf.parse(endDate);

        return date1.before(date2)||date1.equals(date2);

    }
}
