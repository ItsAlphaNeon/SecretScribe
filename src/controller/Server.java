package controller;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.net.URI;

import model.Message;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;


public class Server {
    private String name;
    private String username;
    private String ip;
    private int port;
    private SecretScribe secretScribe;
    private boolean hasRetried;
    public long lastTimeHeartbeatReceived;

    public boolean checkIfConnected() {
        return (client != null);
    }

    public Server(String serverIP, String username, SecretScribe secretScribe) {
        this.ip = serverIP;
        // determine if port is specified, if not use the port the ip is pointing to
        if (serverIP.contains(":")) {
            this.port = Integer.parseInt(serverIP.substring(serverIP.indexOf(":") + 1));
            this.ip = serverIP.substring(0, serverIP.indexOf(":"));
            // Start the server with the specified port
        } else {
            this.port = 8080; // default port for this application
            // Start the server
        }
        this.username = username;
        this.secretScribe = secretScribe;
        RunServer(this.ip, this.port, this.username);
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getServerName() {
        return name;
    }

    private WebSocketClient client;

    private boolean isAuthenticated = false;

    public void RunServer(String ip, int port, String username) {
        // Create a new thread to run the server
        Thread serverThread = new Thread(() -> {
            try {
                // Create a WebSocketClient instance
                client = new WebSocketClient(new URI("ws://" + ip + ":" + port)) {
                    @Override
                    public void onOpen(ServerHandshake handshake) {
                        System.out.println("Connected to server: " + ip + ":" + port); // DEBUG
                        // Send an authentication request to the server
                        send("AUTHENTICATE:" + username);
                        System.out.println("Sent authentication request to server"); // DEBUG
                    }

                    @Override
                    public void onMessage(String message) {
                        // Split the message into its parts by the ":" character
                        String[] messageParts = message.split(":", 2);
                        switch (messageParts[0]) {
                            case "MESSAGE_SENT" -> {
                                //splits the message into its parts by the "\n" (name, date, time, content)
                                String[] messageParts2 = messageParts[1].split("~!!~", 4);
                                // Create a new message object
                                Message newMessage = new Message(messageParts2[0], messageParts2[1], messageParts2[2], messageParts2[3]);
                                System.out.println(messageParts2[0] + " " + messageParts2[1] + " " + messageParts2[2] + " " + messageParts2[3]); // DEBUG
                                secretScribe.addToMessages(newMessage);
                                System.out.println("Message received from server: " + messageParts[1]); // DEBUG
                                // Update the message list
                                secretScribe.displayMessages();

                            }
                            case "AUTHENTICATION_FAILED" -> {
                                System.out.println("Authentication failed"); // DEBUG
                                // Close the connection
                                client.close();
                                // Show a message to the user
                                JOptionPane.showMessageDialog(null, "Authentication failed, please try again", "Authentication Failed", JOptionPane.ERROR_MESSAGE);
                            }
                            case "MEMBER_LIST" -> {
                                System.out.println("Received member list from server: " + messageParts[1]); // DEBUG
                                // Update the member list
                                System.out.println(message);
                                // splits the list into each part by the "~!!~" separator
                                String[] memberList = messageParts[1].split("~!!~");
                                /* Format the list to set the textArea like this:
                                - Member 1
                                - Member 2
                                - Member 3
                                 */
                                StringBuilder formattedList = new StringBuilder();
                                for (String member : memberList) {
                                    formattedList.append("- ").append(member).append(" \r \n"); // \r \r is a new line
                                }
                                // Update the member list
                                secretScribe.setMemberList(formattedList.toString());
                            }
                            case "AUTHENTICATED" -> {
                                isAuthenticated = true;
                                System.out.println("Authentication successful"); // DEBUG
                                // Send a request to the server to get the member list
                                askTheServerForTheMemberList(name);
                            }
                            case "HEARTBEAT_RECEIVED" -> {
                                System.out.println("Heartbeat received from server");
                                lastTimeHeartbeatReceived = new Date().getTime();

                                hasRetried = false;
                            } // DEBUG
                            case "NEW_MEMBER" -> {
                                System.out.println("New member joined the server: " + messageParts[1]); // DEBUG
                                newMemberJoined(messageParts[1]);
                                // Update the member list
                                askTheServerForTheMemberList(name);
                            }
                            default -> {
                                System.out.println("Received message from server: " + message); // DEBUG
                            }
                        }
                    }

                    @Override
                    public void onClose(int code, String reason, boolean remote) {
                        System.out.println("WebSocket connection closed"); // DEBUG
                        // retry connection
                        int response = JOptionPane.showConfirmDialog(null, "Connection to server lost, try again?", "Connection Lost", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            // Try to connect again
                            RunServer(ip, port, username);
                        } else {
                            // Exit the application
                            System.exit(0);
                        }
                    }

                    @Override
                    public void onError(Exception ex) {
                        System.out.println("Error: " + ex.getMessage()); // DEBUG
                    }
                };
                // Connect to the server
                client.connect();
            } catch (Exception ignored) {
            }
        });
        // Start the thread
        serverThread.start();
    }

    private void newMemberJoined(String messagePart) {
        // Create a new message object
        Message newMessage = new Message("Server", "00-00-00", "00:00:00", messagePart + " joined the server");
        secretScribe.addToMessages(newMessage);
        // Update the message list
        secretScribe.displayMessages();
    }

    public void sendMessage(String username, String content) {
        try {
            // Get the current date and time
            Date currentDate = new Date();
            String date = String.format("%tF", currentDate); // Format: YYYY-MM-DD
            String time = String.format("%tT", currentDate); // Format: HH:MM:SS

            // Create the message packet
            String message = String.format("MESSAGE_SENT:%s~!!~%s~!!~%s~!!~%s", username, date, time, content);

            // Send the message packet to the server
            client.send(message);

        } catch (Exception e) {
            client = null;
        }
    }

    // let the server know that the user is still alive
    public void sendHeartbeat(String username) {
        if (checkIfConnected()) {
            try {
                // Create the heartbeat packet
                String heartbeat = String.format("HEARTBEAT:%s", username);

                // Send the heartbeat packet to the server
                client.send(heartbeat);

            } catch (Exception e) {
                System.out.println("Error sending heartbeat to server, connection lost");
                client = null;
            }
        }
    }

    public void askTheServerForTheMemberList(String name) {
        // Create the message packet
        String message = "MEMBER_REQUEST:" + name;
        // Send the message packet to the server
        client.send(message);
    }
}

