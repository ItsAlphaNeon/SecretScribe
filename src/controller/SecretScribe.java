package controller;

import view.ChatWindow;
import view.UserInput;

public class SecretScribe {
    String encryptedData;
    String decryptedData;

    public void run() {
        /*
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

        */

        ChatWindow chatWindow = new ChatWindow();
        chatWindow.setVisible(true);

        //keep the application running
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

