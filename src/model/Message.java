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
        this.dateSent = dateSent;
        this.timeSent = timeSent;
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
        return String.format("%s: (%s %s) \n%s", sendersName, dateSent.formatted(), timeSent.formatted(), separateString(content, 100));
    }
}