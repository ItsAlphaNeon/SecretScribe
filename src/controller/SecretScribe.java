package controller;

import model.Message;
import model.Profile;
import view.ChatWindow;
import view.popups.GetUsernamePopUp;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

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


        // pop up to prompt user to enter their name (Creates profile)
        //create a thread to run the pop-up
        boolean validUsername = false;
        while (!validUsername) {
            String username = JOptionPane.showInputDialog("Enter your username:");
            if (username != null && !username.isEmpty() && username.length() <= 20) {
                // username is valid, create profile
                profile = new Profile(username);
                validUsername = true;
            } else {
                // username is invalid, prompt user to enter a valid username
                JOptionPane.showMessageDialog(null, "Invalid username. Please enter a username that is less than 20 characters.");
            }
        }

        //keep the application running
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

