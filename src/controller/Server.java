package controller;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;


public class Server {
    private String name;
    private String username;
    private String ip;
    private int port;
    public boolean isConnected = false;

    public Server(String name, String ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public Server(String serverIP, String username) {
        this.ip = serverIP;
        // determine if port is specified, if not use the port the ip is pointing to
        if (serverIP.contains(":")) {
            this.port = Integer.parseInt(serverIP.substring(serverIP.indexOf(":") + 1));
            this.ip = serverIP.substring(0, serverIP.indexOf(":"));
            // Start the server with the specified port
        } else {
            this.port = 6969; // default port for this application
            // Start the server
        }
        this.username = username;
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


    public void RunServer(String ip, int port, String username) {
        // Create a new thread to run the server
        Thread serverThread = new Thread(() -> {
            try {
                // Create a WebSocketClient instance
                WebSocketClient client = new WebSocketClient(new URI("ws://" + ip + ":" + port)) {
                    @Override
                    public void onOpen(ServerHandshake handshake) {
                        System.out.println("Connected to server: " + ip + ":" + port); // DEBUG
                        // Send an authentication request to the server
                        send("AUTHENTICATE:" + username);
                        System.out.println("Sent authentication request to server"); // DEBUG
                    }

                    @Override
                    public void onMessage(String message) {
                        System.out.println("Received response from server"); // DEBUG
                        // Print the response from the server
                        System.out.println("Server response: " + message); // DEBUG
                        if (message.equals("AUTHENTICATED")) {
                            System.out.println("Server authenticated"); // DEBUG
                            isConnected = true;
                        } else {
                            System.out.println("Server not authenticated"); // DEBUG
                            isConnected = false;
                        }

                    }

                    @Override
                    public void onClose(int code, String reason, boolean remote) {
                        System.out.println("WebSocket connection closed"); // DEBUG
                    }

                    @Override
                    public void onError(Exception ex) {
                        System.out.println("Error connecting to server: " + ip + ":" + port); // DEBUG
                        System.out.println(ex.getMessage());
                        int response = JOptionPane.showConfirmDialog(null, "Could not connect to server, try again?", "Connection Error", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            // Try to connect again
                            RunServer(ip, port, username);
                        } else {
                            // Exit the application
                            System.exit(0);
                        }
                    }
                };
                // Connect to the server
                client.connect();
            } catch (Exception e) {
                System.out.println("Error connecting to server: " + ip + ":" + port); // DEBUG
                System.out.println(e.getMessage());
                int response = JOptionPane.showConfirmDialog(null, "Could not connect to server, try again?", "Connection Error", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    // Try to connect again
                    RunServer(ip, port, username);
                } else {
                    // Exit the application
                    System.exit(0);
                }
            }
        });
        // Start the thread
        serverThread.start();
    }


    public boolean isConnected() {
        return isConnected;
    }



    public void sendMessage(String username, String content) {
        try {
            // Get the current date and time
            Date currentDate = new Date();
            String date = String.format("%tF", currentDate); // Format: YYYY-MM-DD
            String time = String.format("%tT", currentDate); // Format: HH:MM:SS

            // Create the message packet
            String message = String.format("message:%s:%s:%s:%s", username, date, time, content);

            // Connect to the server using WebSocket client
            WebSocketClient client = new WebSocketClient(URI.create("ws://" + ip + ":" + port)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    // WebSocket connection is opened
                    System.out.println("WebSocket connection opened");
                    // Send the message packet to the server
                    send(message);
                    // Close the connection
                    close();
                }

                @Override
                public void onMessage(String s) {
                    // Do nothing
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    // Do nothing
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            };
            client.connect();
        } catch (Exception e) {
            isConnected = false;
        }
    }

    // let the server know that the user is still alive
    public void sendHeartbeat(String username) {
        if (isConnected) {
            try {
                // Create the heartbeat packet
                String heartbeat = String.format("heartbeat:%s", username);

                // Connect to the server using WebSocket client
                WebSocketClient client = new WebSocketClient(URI.create("ws://" + ip + ":" + port)) {
                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {
                        // WebSocket connection is opened
                        System.out.println("WebSocket connection opened");
                        // Send the heartbeat packet to the server
                        send(heartbeat);
                        System.out.println("Sent heartbeat to server");
                        // Close the connection
                        close();
                    }

                    @Override
                    public void onMessage(String s) {
                        // Do nothing
                    }

                    @Override
                    public void onClose(int i, String s, boolean b) {
                        // Do nothing
                    }

                    @Override
                    public void onError(Exception e) {
                        System.out.println("Error sending heartbeat to server, connection lost");
                        isConnected = false;
                    }
                };
                client.connect();
            } catch (Exception e) {
                System.out.println("Error sending heartbeat to server, connection lost");
                isConnected = false;
            }
        }
    }
}

