package model;

import controller.FileIO;
import controller.Server;

import java.io.File;
import java.util.Collection;

public class ServerList {
    private Server[] servers;

    public ServerList() {
        servers = new Server[0];
    }

    public static void saveServer(String serverIP) {
        // check to see if the file exists
        File file = new File("servers.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        // check if server is already saved
        String[] savedServers = FileIO.readFromFile("servers.txt").split(" ");
        for (String savedServer : savedServers) {
            if (savedServer.equals(serverIP)) {
                System.out.println("Server already saved"); // DEBUG
                return;
            }
        }
        // if not saved, save it
        // save the server to a file "servers.txt" in a way that can be read by the loadServer method
        FileIO.writeToFile("servers.txt", FileIO.readFromFile("servers.txt") + " " + serverIP + " ");
        System.out.println("Server saved"); // DEBUG
    }

    public static void deleteServer(String serverIP) {
        // check to see if the file exists
        File file = new File("servers.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        // check if server is already saved
        String[] savedServers = FileIO.readFromFile("servers.txt").split(" ");
        for (String savedServer : savedServers) {
            if (savedServer.equals(serverIP)) {
                // if saved, delete it
                // save the server to a file "servers.txt" in a way that can be read by the loadServer method
                FileIO.writeToFile("servers.txt", FileIO.readFromFile("servers.txt").replace(serverIP + " ", ""));
                System.out.println("Server deleted"); // DEBUG
                return;
            }
        }
        System.out.println("Server not saved"); // DEBUG
    }

    public void addServer(Server server) {
        Server[] temp = new Server[servers.length + 1];
        for (int i = 0; i < servers.length; i++) {
            temp[i] = servers[i];
        }
        temp[servers.length] = server;
        servers = temp;
    }

    public void removeServer(int index) {
        Server[] temp = new Server[servers.length - 1];
        for (int i = 0; i < index; i++) {
            temp[i] = servers[i];
        }
        for (int i = index; i < temp.length; i++) {
            temp[i] = servers[i + 1];
        }
        servers = temp;
    }

    public Server getServer(int index) {
        return servers[index];
    }

    public int getServerCount() {
        return servers.length;
    }

    public String[] getServerNames() {
        String[] serverNames = new String[servers.length];
        for (int i = 0; i < servers.length; i++) {
            serverNames[i] = servers[i].getName();
        }
        return serverNames;
    }

    public String[] getServerIPs() {
        String[] serverIPs = new String[servers.length];
        for (int i = 0; i < servers.length; i++) {
            serverIPs[i] = servers[i].getIp();
        }
        return serverIPs;
    }

    public int[] getServerPorts() {
        int[] serverPorts = new int[servers.length];
        for (int i = 0; i < servers.length; i++) {
            serverPorts[i] = servers[i].getPort();
        }
        return serverPorts;
    }

    public static String[] getServerList() {
        // read from file "servers.txt" and return the list of servers as a String[]
        return FileIO.readFromFile("servers.txt").split(" ");
    }
}
