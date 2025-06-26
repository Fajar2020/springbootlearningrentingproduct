package com.springrent.rent_admin_backend.helpers;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class FilterDateParams {
    public static Timestamp getDateTimestamp(Date date, Integer differentDay) {
        Calendar cal = Calendar.getInstance();
        if (date == null) {
            cal.add(Calendar.DAY_OF_MONTH, differentDay);  // subtract one day
            date = cal.getTime();
        }

        return new Timestamp(date.getTime());
    }
}
