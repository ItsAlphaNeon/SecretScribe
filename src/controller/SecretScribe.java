package controller;

import com.formdev.flatlaf.intellijthemes.FlatDarkFlatIJTheme;
import model.Message;
import model.Profile;
import model.ServerList;
import view.SecretScribeFrame;
import view.popups.ServerConnectionFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;


public class SecretScribe {
    Profile profile;
    Server server;
    ServerList serverList = new ServerList();
    SecretScribeFrame secretScribeFrame;
    ServerConnectionFrame serverConnectionFrame;
    ArrayList<Message> messages = new ArrayList<>();
    Crypt crypt = new Crypt();


    public void run() {
        // prompt the user for their username
        usernamePopUp();
        // prompt the user for the server ip
        serverPopUp();
        // create the main window
        setSecretScribeWindowCreate();
        // setup the timers
        setupTimers();

        //MAKE SURE TO UPDATE ONLY WHEN THERE'S A NEW MESSAGE

        //keep the application running while the window is open
        keepRunning();
    }

    private void keepRunning() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void serverPopUp() {
        // create a new server connection frame
        ServerConnectionFrame serverConnectionFrame = new ServerConnectionFrame();
        // set the server connection frame to visible
        serverConnectionFrame.setVisible(true);
        // ask the user if they want to close the program if they close the server connection frame
        serverConnectionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // add an action listener to the server connection frame
        // pass the serverconnectionframe to the ServerList class to populate the combo box
        serverList.setServerConnectionFrame(Objects.requireNonNull(serverConnectionFrame));
        populateServerList(serverConnectionFrame);

        serverConnectionFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // ask the user if they want to close the program
                int result = JOptionPane.showConfirmDialog(serverConnectionFrame, "Are you sure you want to close the program?", "Exit Program", JOptionPane.YES_NO_OPTION);
                // if the user clicks yes, close the program
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        // make the server connection frame not resizable
        serverConnectionFrame.setResizable(false);
        //make sure the combo box is editable
        serverConnectionFrame.serverIPListComboBox.setEditable(true);
        // add an action listener to the ok button
        serverConnectionFrame.okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get the server ip from the server connection frame
                String serverIP = (String) serverConnectionFrame.serverIPListComboBox.getSelectedItem();
                // make sure the IP is not null
                if (serverIP != null) {
                    // create a new server
                    server = new Server(serverIP);
                    // set the title of the window to the server name
                    setWindowTitle(secretScribeFrame, server.getName());
                    // close the server connection frame
                    serverConnectionFrame.dispose();
                } else {
                    // if the IP is null, display an error message
                    JOptionPane.showMessageDialog(serverConnectionFrame, "Please enter a valid IP address");
                }
            }
        });
        // add an action listener to the save button
        serverConnectionFrame.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get the server ip from the server connection frame
                String serverIP = (String) serverConnectionFrame.serverIPListComboBox.getSelectedItem();
                // make sure the IP is not null
                if (serverIP != null) {
                    // save the IP to the server list file
                    serverList.saveServer(serverIP);
                    // inform the user that the IP was saved
                    JOptionPane.showMessageDialog(serverConnectionFrame, "Server IP saved");

                } else {
                    // if the IP is null, display an error message
                    JOptionPane.showMessageDialog(serverConnectionFrame, "Please enter a valid IP address");
                }
            }
        });
        // add an action listener to the Delete button
        serverConnectionFrame.deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get the server ip from the server connection frame
                String serverIP = (String) serverConnectionFrame.serverIPListComboBox.getSelectedItem();
                // make sure the IP is not null
                if (serverIP != null) {
                    // delete the IP from the server list file
                    serverList.deleteServer(serverIP);
                    // inform the user
                } else {
                    // if the IP is null, display an error message
                    JOptionPane.showMessageDialog(serverConnectionFrame, "Please enter a valid IP address");
                }
            }
        });
        while (server == null) {
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
                    checkIfPinIsValid();
                }
                // if there is a valid pin, allow the user to send messages
                if (controller.UserInput.isValidPin(secretScribeFrame.getPinField())) {
                    // if the user clicked the send button
                    if (secretScribeFrame.ifSendButtonClicked()) {
                        // check to see if the message field is empty
                        if (secretScribeFrame.getMessageField().length() > 0) {
                            // create a new message
                            Message msg = createMessage(secretScribeFrame.getMessageField());
                            // TODO: encrypt the message and send to server
                        }
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

    private void checkIfPinIsValid() {
        if (UserInput.isValidPin(secretScribeFrame.getPinField())) {
            // allow the user to send messages
            secretScribeFrame.setButtonClickable(secretScribeFrame.getSendButton(), true);
            // create a toast to notify the user that the pin is valid
            secretScribeFrame.createToast("Pin is valid", "Success");

        } else {
            // pin is invalid, enable the pin field
            secretScribeFrame.setPasswordFieldEditable(secretScribeFrame.getPinFieldReference(), true);
            // disable the send button
            secretScribeFrame.setButtonClickable(secretScribeFrame.getSendButton(), false);
            // create a toast to notify the user that the pin is invalid
            secretScribeFrame.createToast("Pin is invalid", "Error");
        }
    }

    private void usernamePopUp() {
        // theme the pop-up
        try {
            // dark mode for swing
            UIManager.setLookAndFeel(new FlatDarkFlatIJTheme());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        secretScribeFrame = new SecretScribeFrame(600, 400);

        // disable the send button
        secretScribeFrame.setButtonClickable(secretScribeFrame.getSendButton(), false);
        // disable the pin field
        secretScribeFrame.setPasswordFieldEditable(secretScribeFrame.getPinFieldReference(), false);
        // set the window title
        secretScribeFrame.setName("SecretScribe - " + profile.getName());

    }

    public void sendTestMessage(String content) { // Debug method TODO: Delete this
        if (content != null && content.length() > 0) {
            try {
                content = crypt.encrypt(content);
            } catch (Exception ex) {
            }
            Message msg = createMessage(content);
            messages.add(msg);
        }
    }

    // display the messages in the chat window
    // debug only, local messages in the array
    public void displayMessages() {
        // clear the chat window
        secretScribeFrame.clearChatWindow();
        for (Message msg : messages) {
            try {
                msg.setContent(crypt.decrypt(msg.getContent()));
            } catch (Exception ex) {
                secretScribeFrame.addMessage(msg);
            }
        }
    }

    // set up the gui listeners for the main window
    private void setupListeners() {
        // add a listener for the send button
        secretScribeFrame.getSendButton().addActionListener(e -> {
            sendButtonClicked();
        });
        // add a listener for the check pin button
        secretScribeFrame.getCheckPinButton().addActionListener(e -> {
            checkPinButtonClicked();
        });
        secretScribeFrame.getMessageFieldReference().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // call sendButtonClicked() when the user presses enter
                sendButtonClicked();
            }
        });
    }

    private void checkPinButtonClicked() {
        // check if the pin is valid
        checkIfPinIsValid();
    }

    private void sendButtonClicked() {
        // check to see if the message field is empty
        if (secretScribeFrame.getMessageField().length() > 0) {
            // create a new message
            Message msg = createMessage(secretScribeFrame.getMessageField());
            try {
                msg.setContent(crypt.encrypt(msg.getContent()));
                // TODO: SEND THE MESSAGE TO THE SERVER
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        // clear the message field
        secretScribeFrame.clearMessageField();
    }

    public void populateServerList(ServerConnectionFrame serverConnectionFrame) {
        // populate the server ip list combo box, making sure its not null
        ServerList.getServerList();
        //clear the combo box
        serverConnectionFrame.serverIPListComboBox.removeAllItems();
        for (String serverIP : ServerList.getServerList()) {
            serverConnectionFrame.serverIPListComboBox.addItem(serverIP);
        }
    }
}


