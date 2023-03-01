package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    public static LocalDate getDate(String dateString) {
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date;
    }


    public static String getString(LocalDate date) {
        return formatter.format(date);
    }

    public static boolean inRange(LocalDate date, LocalDate minDate, LocalDate maxDate) {
        return (date.isAfter(minDate) && date.isBefore(maxDate) || date.isEqual(minDate) || date.isEqual(maxDate));
    }
}
