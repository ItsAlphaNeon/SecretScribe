package controller;

import view.UserInput;

public class SecretScribe {
    String encryptedData;
    String decryptedData;

    public void run(){
        try {
            Crypt crypt = new Crypt();
            crypt.init();
            String encryptedData = crypt.encrypt(UserInput.getString("Message: "));
            String decryptedData = crypt.decrypt(encryptedData);
            System.out.println("Encrypted Data : " + encryptedData);
            System.out.println("Decrypted Data : " + decryptedData);

        } catch (Exception ex){
            System.out.println(encryptedData);
        }


    }
}

