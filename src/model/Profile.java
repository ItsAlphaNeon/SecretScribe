package model;

public class Profile {
    private String name;
    private String pin;


    public Profile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public void getPin(String encryptKey) {
        this.pin = encryptKey;
    }
}
