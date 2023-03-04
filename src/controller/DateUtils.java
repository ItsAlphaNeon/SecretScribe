package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    // format for date
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    // get date from string
    public static LocalDate getDate(String dateString) {
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date;
    }

    public static String getString(LocalDate date) {
        return formatter.format(date);
    }

    // check if date is in range
    public static boolean inRange(LocalDate date, LocalDate minDate, LocalDate maxDate) {
        return (date.isAfter(minDate) && date.isBefore(maxDate) || date.isEqual(minDate) || date.isEqual(maxDate));
    }

    // get current time
    public static LocalTime getTime() {
        return LocalTime.now();
    }
}
