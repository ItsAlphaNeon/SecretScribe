package controller;

import model.Message;
import model.Profile;
import view.SecretScribeFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class SecretScribe {
    Profile profile;
    SecretScribeFrame secretScribeFrame;
    ArrayList<Message> messages = new ArrayList<>();


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

        usernamePopUp();
        setSecretScribeWindowCreate();
        // setup the timers
        setupTimers();

        //MAKE SURE TO UPDATE ONLY WHEN THERE'S A NEW MESSAGE

        // Set the window title to the user's name
        setWindowTitle(secretScribeFrame, profile.getName());

        //keep the application running while the window is open
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setWindowTitle(SecretScribeFrame secretScribeWindow, String name) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                secretScribeWindow.setTitle("Secret Scribe - " + name);
            }
        });
    }

    private void setupTimers() {
        // create a timer to update the gui every 100ms
        Timer timer = new Timer(100, e -> updateGUI());
        timer.start();

        // create a timer to update the messages every 1 second
        Timer messageTimer = new Timer(1000, e -> displayMessages());
        messageTimer.start();
    }

    public Message createMessage(String content) {
        if (!(profile == null)) {
            System.out.println("The profile is not null"); // DEBUG
            Message msg = null;
            if (content.length() > 0) {
                msg = new Message(profile.getName(), DateUtils.getDate().toString(), DateUtils.getTime().toString(), content);
            }
            if (msg != null) {
                return msg;
            }
        }
        System.out.println("The profile is null"); // DEBUG
        return null;
    }

    private void updateGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // wait for the user to click the check pin button
                if (secretScribeFrame.getCheckPinButtonClicked()) {
                    // check if the pin is valid
                    if (controller.UserInput.isValidPin(secretScribeFrame.getPinField())) {
                        // allow the user to send messages
                        secretScribeFrame.setButtonClickable(secretScribeFrame.getSendButton(), true);
                        // create a toast to notify the user that the pin is valid
                        secretScribeFrame.createToast("Pin is valid", "Success");

                    } else {
                        // pin is invalid, enable the pin field
                        secretScribeFrame.setPasswordFieldEditable(secretScribeFrame.getPinFieldReference(), true);
                        // disable the send button
                        secretScribeFrame.setButtonClickable(secretScribeFrame.getSendButton(), false);
                        secretScribeFrame.createToast("Pin is invalid", "Error");
                    }
                }
                // if there is a valid pin, allow the user to send messages
                if (controller.UserInput.isValidPin(secretScribeFrame.getPinField())) {
                    // if the user clicked the send button
                    if (secretScribeFrame.ifSendButtonClicked()) {
                        // create a new message
                        Message msg = createMessage(secretScribeFrame.getMessageField());
                        // TODO: encrypt the message, prepare to send it to the server

                        // clear the message field
                        secretScribeFrame.clearMessageField();
                    }
                } else {
                    secretScribeFrame.setButtonClickable(secretScribeFrame.getSendButton(), false);
                }
            }
        });
        // check if the user clicked the send button
        if (secretScribeFrame.ifSendButtonClicked()) {
            // create a new message
            sendTestMessage(secretScribeFrame.getMessageField());
            // clear the message field
            secretScribeFrame.clearMessageField();
        }
    }

    private void usernamePopUp() {
        // pop up to prompt user to enter their name (Creates profile)
        //create a thread to run the pop-up
        boolean validUsername = false;
        while (!validUsername) {
            // prompt user to enter a username using JOptionPane
            // give them an option to cancel and exit the program
            String username = JOptionPane.showInputDialog(null, "Enter a username:", "Secret Scribe", JOptionPane.PLAIN_MESSAGE);
            // check if the user clicked cancel
            if (username == null) {
                // user clicked cancel, prompt them to confirm whether they want to exit the program
                int confirmExit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (confirmExit == JOptionPane.YES_OPTION) {
                    // user confirmed exit, exit the program
                    System.exit(0);
                } else {
                    // user wants to keep running the program, show the username prompt again
                    continue;
                }
            } else if (username.isEmpty() || username.length() > 20) {
                // username is invalid, prompt user to enter a valid username
                JOptionPane.showMessageDialog(null, "Invalid username. Please enter a username that is less than 20 characters.");
            } else {
                // username is valid, create profile
                profile = new Profile(username);
                // debug
                System.out.println("Profile created: " + profile.getName());
                validUsername = true;
            }
        }
    }

    private void setSecretScribeWindowCreate() {
        // create a new SecretScribeFrame
        secretScribeFrame = new SecretScribeFrame();
        // set the window to visible
        secretScribeFrame.setVisible(true);

        // disable the send button
        secretScribeFrame.setButtonClickable(secretScribeFrame.getSendButton(), false);
        // disable the pin field
        secretScribeFrame.setPasswordFieldEditable(secretScribeFrame.getPinFieldReference(), false);


        // add a listener to the send button
        secretScribeFrame.getSendButton().addActionListener(e -> {
            // create a new message
            Message msg = createMessage(secretScribeFrame.getMessageField());
        });

        // add a listener to the check pin button
        secretScribeFrame.getCheckPinButton().addActionListener(e -> {
            // check if the pin is valid
            if (controller.UserInput.isValidPin(secretScribeFrame.getPinField())) {
                // allow the user to send messages
                secretScribeFrame.setButtonClickable(secretScribeFrame.getSendButton(), true);
                // create a toast to notify the user that the pin is valid
                secretScribeFrame.createToast("Pin is valid", "Success");
            } else {
                // pin is invalid, enable the pin field
                secretScribeFrame.setPasswordFieldEditable(secretScribeFrame.getPinFieldReference(), true);
                // disable the send button
                secretScribeFrame.setButtonClickable(secretScribeFrame.getSendButton(), false);
                secretScribeFrame.createToast("Pin is invalid", "Error");
            }
        });
    }

    public void sendTestMessage(String content) { // Debug method
        Message msg = createMessage(content);
        messages.add(msg);
    }

    // display the messages in the chat window
    // debug only, local messages in the array
    public void displayMessages() {
        // clear the chat window
        secretScribeFrame.clearChatWindow();
        for (Message msg : messages) {
            secretScribeFrame.addMessage(msg);
        }
    }
}

