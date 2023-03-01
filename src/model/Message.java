package model;

public class Message {
    private String sendersName;
    private String dateSent;
    private String timeSent;
    private String content;

    public Message(String sendersName, String dateSent, String timeSent, String content){
        this.sendersName = sendersName;
        this.dateSent = dateSent;
        this.timeSent = timeSent;
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
}
