package controller;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FileIO {
    public static final String ROOT = "src/resource/";

    public static File createFile(String pathname) throws IOException {
        File file = new File(pathname);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public static File getFile(String pathname) throws IOException {
        File file = new File(pathname);
        if (!file.exists()) {
            return null;
        }
        return file;
    }

    public static String[] getFilenames(String pathname) {
        File file = new File(pathname);

        List<String> filenames = Arrays.asList(file.list());
        // files in format of "mm-dd-yyyy.txt"
        for (int i = 0; i < filenames.size();i++) {
            String fileName = filenames.get(i);
            // remove file extension (.)
            filenames.set(i, fileName.substring(0, fileName.lastIndexOf(".")));
        }
        return filenames.toArray(new String[0]);
    }

    public static String[] getFilenames(File file) {
        List<String> filenames = Arrays.asList(file.list());
        // files in format of "mm-dd-yyyy.txt"
        for (int i = 0; i < filenames.size();i++) {
            String fileName = filenames.get(i);
            // remove file extension (.)
            filenames.set(i, fileName.substring(0, fileName.lastIndexOf(".")));
        }
        return filenames.toArray(new String[0]);
    }


    public static void writeToFile(String pathname, String string) {
        writeToFile(new File(pathname), string);
    }

    public static void writeToFile(File file, String string) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(string);
            writer.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String readFromFile(String pathname) {
        return readFromFile(new File(pathname));
    }

    public static String readFromFile(File file) {
        StringBuilder sb = new StringBuilder((int)file.length());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String lineString;
            while ((lineString = reader.readLine()) != null) {
                sb.append(lineString);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return sb.toString();
    }
}
