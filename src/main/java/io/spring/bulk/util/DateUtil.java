package io.spring.bulk.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String getYesterDay() {
        LocalDate rawYesterDay = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        return formatter.format(rawYesterDay);
    }

}
