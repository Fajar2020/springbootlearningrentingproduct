package com.springrent.rent_admin_backend.helpers;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import static com.springrent.rent_admin_backend.helpers.FilterDateParams.getDateTimestamp;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FilterDateParamsTest {

    @Test
    public void testGetDateTimestamp() {
        try {
            Clock.fixed(Instant.parse("2025-06-18T00:00:00Z"), ZoneId.systemDefault());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String previousTwoDays = "2025-05-16";
            Date datePreviousTwoDays = formatter.parse(previousTwoDays);

            Timestamp startTime = getDateTimestamp(null, -2);
            assertEquals(new Timestamp(datePreviousTwoDays.getTime()), startTime);

            String nextSevenDays = "2025-05-25";
            Date dateNextSevenDays = formatter.parse(nextSevenDays);

            Timestamp endTime = getDateTimestamp(null, 7);
            assertEquals(new Timestamp(dateNextSevenDays.getTime()), endTime);

        } catch (ParseException e) {
            System.err.println("Invalid date format: " + e.getMessage());
        }
    }
}
