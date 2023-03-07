package controller;

import com.formdev.flatlaf.intellijthemes.FlatDarkFlatIJTheme;
import view.popups.Connecting;
import view.popups.ServerConnectionFrame;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

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
            this.username = username;
            // Start the server with the specified port
            RunServer(this.ip, this.port, this.username);
        } else {
            this.port = 6969; // default port for this application
            this.username = username;
            // Start the server
            RunServer(this.ip, this.port, this.username);
        }
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
                // Create a new socket
                Socket socket = new Socket(ip, port);
                System.out.println("Connected to server: " + ip + ":" + port); // DEBUG
                // Create input and output streams
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.println("Created input and output streams"); // DEBUG

                // Send an authentication request to the server
                out.writeUTF("AUTHENTICATE");
                out.writeUTF(username);
                System.out.println("Sent authentication request to server"); // DEBUG

                // Receive the response from the server
                String response = in.readUTF();
                System.out.println("Received response from server"); // DEBUG
                // Print the response from the server
                System.out.println("Server response: " + response); // DEBUG
                if (response.equals("AUTHENTICATED")) {
                    System.out.println("Server authenticated"); // DEBUG
                    isConnected = true;
                } else {
                    System.out.println("Server not authenticated"); // DEBUG
                    isConnected = false;
                }
                // Close the socket
                socket.close();
                System.out.println("Closed socket"); // DEBUG
            } catch (IOException e) {
                System.out.println("Error connecting to server: " + ip + ":" + port); // DEBUG
                System.out.println(e.getMessage());
            }
            int response = JOptionPane.showConfirmDialog(null, "Could not connect to server, try again?", "Connection Error", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                // Try to connect again
                RunServer(ip, port, username);
            } else {
                // Exit the application
                System.exit(0);
            }
        });
        // Start the thread
        serverThread.start();
        // Wait until isConnected is true

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

            // Connect to the server
            Socket socket = new Socket("localhost", 6969);

            // Send the message packet to the server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(message);

            // Close the connection
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

