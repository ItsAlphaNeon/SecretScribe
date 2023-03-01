package model;

public class Profile {
    private String name;
    private String encryptKey;
    private String decryptKey;

    public Profile(String name, String encryptKey, String decryptKey) {
        this.name = name;
        this.encryptKey = encryptKey;
        this.decryptKey = decryptKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    public String getDecryptKey() {
        return decryptKey;
    }

    public void setDecryptKey(String decryptKey) {
        this.decryptKey = decryptKey;
    }
}
