package controller;

import controller.DateUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class UserInput {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static String getString() {
        String string = "";
        try {
            string = reader.readLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return string;
    }

    public static String getString(String prompt) {
        System.out.print(prompt);
        return getString();
    }

    public static Boolean isValidPin(String pin) {
        // check if pin is 4 digits and is an integer
        return (pin.length() >= 1);
    }


    public static int getIntegerEqualTo(String prompt, int min, int max) {
        while (true) {
            String string = getString(prompt);
            try {
                int i = Integer.parseInt(string);

                if (i >= min && i <= max) {
                    return i;
                }
            } catch (Exception ex) {
                System.out.println("Invalid integer");
            }
        }
    }

    public static String getStringNonEmpty(String prompt) {
        while (true) {
            String string = getString(prompt);
            if (!string.isEmpty()) {
                return string;
            }
        }
    }

    public static boolean getBoolean(String prompt, String rightString, String wrongString) {
        while (true) {
            String string = getString(prompt);
            if (rightString.equalsIgnoreCase(string)) return true;
            if (wrongString.equalsIgnoreCase(string)) return false;

        }
    }

    public static String getMultipleLineString(String prompt, String end) {
        System.out.println(prompt);
        String string = "";
        while (true) {
            String line = getString();
            if (line.equalsIgnoreCase(end)) break;
            string += line + '\n';
        }

        return string;
    }

    public static LocalDate getValidDate(String prompt) {
        LocalDate date = null;
        String dateString = "";
        while (true) {
            dateString = getStringNonEmpty(prompt);
            try {
                date = DateUtils.getDate(dateString);
                return date;
            } catch (Exception ex) {
                System.out.println("Invalid date");
            }

        }
    }
}
