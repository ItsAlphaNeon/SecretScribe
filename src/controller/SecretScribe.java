package controller;

import com.formdev.flatlaf.intellijthemes.FlatDarkFlatIJTheme;
import model.Message;
import model.Profile;
import model.ServerList;
import view.SecretScribeFrame;
import view.popups.ServerConnectionFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SecretScribe {
    Profile profile;
    Server server;
    ServerList serverList = new ServerList();
    SecretScribeFrame secretScribeFrame;
    ServerConnectionFrame serverConnectionFrame;
    ArrayList<Message> messages = new ArrayList<>();
    private String pin = "0000";


    public void run() {
        // prompt the user for their username
        usernamePopUp();
        // prompt the user for the server ip
        serverPopUp();
        // Wait for the user to connect to the server
        waitForConnection();
        // create the main window
        chatWindowPopup();
        // setup the timers
        setupTimers();

        //MAKE SURE TO UPDATE ONLY WHEN THERE'S A NEW MESSAGE

        //keep the application running while the window is open
        keepRunning();
    }

    private void waitForConnection() {

        while (server == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
        // create a SwingWorker to run the server connection frame
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // create a new server connection frame
                Thread serverConnectionFrameThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // create a new server connection frame
                        ServerConnectionFrame serverConnectionFrame = new ServerConnectionFrame();
                        // set the server connection frame to visible
                        serverConnectionFrame.setVisible(true);
                        // ask the user if they want to close the program if they close the server connection frame
                        serverConnectionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
                                if (server == null && serverIP != null) {
                                    // create a new server
                                    server = new Server(serverIP, profile.getName(), SecretScribe.this);
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
                                    // update the server list
                                    populateServerList(serverConnectionFrame);
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
                });
                serverConnectionFrameThread.start();
                return null;
            }
        };
        // execute the SwingWorker
        worker.execute();
    }

    private void setupTimers() {
        // create a timer to update the gui every 100ms
        Timer timer = new Timer(100, e -> updateGUI());
        timer.start();

        // Create a timer to send a heartbeat every 5 seconds
        Timer heartbeatTimer = new Timer(5000, e -> server.sendHeartbeat(profile.getName()));
        heartbeatTimer.start();

        Timer askTheServerForTheMemberListTimer = new Timer(14000, e -> server.askTheServerForTheMemberList(profile.getName()));
        askTheServerForTheMemberListTimer.start();
    }

    public Message createMessage(String content) {
        if (profile != null) {
            Message msg = null;
            if (content.length() > 0) {
                msg = new Message(profile.getName(), DateUtils.getDate().toString(), DateUtils.getTime().toString(), content);
            }
            return msg;
        }
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
                if (controller.UserInput.isValidPin(pin)) {
                    // if the user clicked the send button
                    if (secretScribeFrame.ifSendButtonClicked()) {
                        // check to see if the message field is empty
                        if (secretScribeFrame.getMessageField().length() > 0) {
                            // create a new encrypted message
                            createEncryptedMessage(secretScribeFrame.getMessageField());
                            // clear the message field
                            secretScribeFrame.clearMessageField();
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
            //create a new message
            createEncryptedMessage(secretScribeFrame.getMessageField());
            // clear the message field
            secretScribeFrame.clearMessageField();
        }
        // Change the serverNameLabel to the server name
        secretScribeFrame.setServerNameLabel(server.getName());
        // Make sure the password field is editable
        secretScribeFrame.setPasswordFieldEditable(secretScribeFrame.getPinFieldReference(), true);
        
    }

    private void createEncryptedMessage(String content) {
        if (content != null && content.length() > 0) {
            try {
                // encrypt the msg
                content = Crypt.encrypt(content, pin);
            } catch (Exception ex) {
            }
            // send the message to the server
            server.sendMessage(profile.getName(), content);
        }
    }

    private void checkIfPinIsValid() {
        if (UserInput.isValidPin(secretScribeFrame.getPinField())) {
            pin = secretScribeFrame.getPinField();
            // allow the user to send messages
            secretScribeFrame.setButtonClickable(secretScribeFrame.getSendButton(), true);
            // create a toast to notify the user that the pin is valid
            secretScribeFrame.createToast("Pin is valid", "Success");
        } else {
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

    private void chatWindowPopup() {
        // create a new SecretScribeFrame
        secretScribeFrame = new SecretScribeFrame(600, 400);
    }

    public void addToMessages(Message msg) {
        messages.add(msg);
    }

    // display the messages in the chat window
    // debug only, local messages in the array
    public void displayMessages() {
        // clear the chat window
        secretScribeFrame.clearChatWindow();
        for (Message msg : messages) {
            try {
                msg.setContent(Crypt.decrypt(msg.getContent(), pin));
                System.out.println("Was decrypted");
                secretScribeFrame.addMessage(msg);
            } catch (Exception ex) {
                secretScribeFrame.addMessage(msg);
                System.out.println("Was not decrypted");
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
    } // TODO add listeners for the enter key on all kinds of things

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
                msg.setContent(Crypt.encrypt(msg.getContent(), pin));
                server.sendMessage(profile.getName(), msg.getContent());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        // clear the message field
        secretScribeFrame.clearMessageField();
    }

    public void populateServerList(ServerConnectionFrame serverConnectionFrame) {
        //clear the combo box
        serverConnectionFrame.serverIPListComboBox.removeAllItems();
        // take each string from the serverList and add it to the combo box

    }

    public void setMemberList(String members) {
        secretScribeFrame.getMemberList().setText(members);
    }
}


