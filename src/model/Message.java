package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class Message {
    private String sendersName;
    private String dateSent;
    private String timeSent;
    private String content;

    public Message(String sendersName, String dateSent, String timeSent, String content) {
        this.sendersName = sendersName;
        this.content = content;
    }

    public String getSendersName() {
        return sendersName;
    }

    public String getDateSent() {
        return dateSent;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public static String separateString(String input, int maxLength) {
        StringBuilder sb = new StringBuilder();
        int startIndex = 0;
        int endIndex = maxLength;
        while (startIndex < input.length()) {
            if (endIndex >= input.length()) {
                endIndex = input.length();
            } else {
                while (endIndex > startIndex && input.charAt(endIndex) != ' ') {
                    endIndex--;
                }
            }
            if (endIndex == startIndex) {
                // no space found and it's the last substring, append remaining characters
                if (endIndex == input.length() - 1) {
                    sb.append("          "); // 10 spaces indentation
                    sb.append(input.substring(startIndex));
                    break;
                }
                // no space found, break at maxLength characters anyway
                endIndex = startIndex + maxLength;
            }
            sb.append("          "); // 10 spaces indentation
            sb.append(input.substring(startIndex, endIndex));
            sb.append("\n"); // line break
            startIndex = endIndex;
            endIndex = startIndex + maxLength;
        }
        return sb.toString();
    }






    public String toString() {
        /*
        // format the message to be displayed in the chat window
        StringBuilder contentString = new StringBuilder();
        String line = "";
        int runs = 1;
        // if the content is longer than 50 characters, split it into multiple lines
        // split to the nearest word

        if (content.length() > 100) {
            // loop through the content
            for (int i = 100; i < content.length(); i++) {
                // if the current character is a space, split the content at 50 characters
                
                if (i > contentString.length() + 100) {
                    // get the current line of the content
                    line = content.substring((contentString.length()-10*runs), i);
                    runs++;
                    // get the index of the last space in the line
                    int lastSpace = line.lastIndexOf(" ");
                    // if the last character of the line is a space, add the line to the content string
                    contentString.append(line, 0, lastSpace).append("\n        ");
                    i = lastSpace
                    ;
                }
            }
            // add the last line of the content to the content string
            contentString.append(content.substring(contentString.length()));
        }
        // if the content is more than 50 characters and doesn't have a space split it at 50 characters
        else if (content.length() >= 50) {
            // loop through the content
            for (int i = 50; i < content.length(); i++) {
                // if there is no space in the content, split the content at 50 characters
                if (content.indexOf(" ") >= 50) {
                    // add the line to the content string
                    contentString.append(content, i - 50, i).append("-\n        ");
                }
            }
        }
        else {
            contentString = new StringBuilder(content);
        }
        // return the formatted message
        */
        return String.format("%s: (%s %s) \n%s", sendersName, dateSent.formatted(), timeSent.formatted(), separateString(content, 100));
    }
}