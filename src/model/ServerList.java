package model;

import controller.FileIO;
import controller.SecretScribe;
import controller.Server;
import view.popups.ServerConnectionFrame;

import java.io.File;
import java.util.Collection;


public class ServerList {
    private Server[] servers;
    public ServerConnectionFrame serverConnectionFrame;

    public ServerList() {
        servers = new Server[0];
    }

    public void saveServer(String serverIP) {
        // check to see if the serverIP already exists in the file "servers.txt"
        if (!serverIP.contains("~!!~")) {
            String[] savedServers = FileIO.readFromFile("servers.txt").split("~!!~");
            for (String savedServer : savedServers) {
                savedServer = savedServer.trim();
                if (savedServer.contains(serverIP)) {
                    // if it does, do not save it again
                    //TODO: ADD THE SERVER LIST REFRESH
                    return;
                }
            }
        }
        // if it doesn't, save it
        // save the server to a file "servers.txt" in a way that can be read by the getServerList method
        FileIO.writeToFile("servers.txt", FileIO.readFromFile("servers.txt") + serverIP + "~!!~");
        System.out.println("Server saved");
        // refresh the server list
        //TODO: ADD THE SERVER LIST REFRESH
    }

    public void deleteServer(String serverIP) {
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
        String[] savedServers = FileIO.readFromFile("servers.txt").split("~!!~");
        for (String savedServer : savedServers) {
            savedServer = savedServer.trim();
            System.out.println("Saved server: " + savedServer); // DEBUG
            if (savedServer.contains(serverIP)) {
                // if saved, delete it
                // save the server to a file "servers.txt" in a way that can be read by the getServerList method
                String updatedServers = FileIO.readFromFile("servers.txt").replace(serverIP, "");
                System.out.println("Updated servers: " + updatedServers); // DEBUG
                FileIO.writeToFile("servers.txt", updatedServers);
                System.out.println("Server deleted"); // DEBUG
                // refresh the server list
                return;
            }
        }
        // if the server was not found, print a message
        System.out.println("Server Not Deleted - Error"); // DEBUG
    }


    public void addServer(Server server) {
        Server[] temp = new Server[servers.length + 1];
        System.arraycopy(servers, 0, temp, 0, servers.length);
        temp[servers.length] = server;
        servers = temp;
    }

    public void removeServer(int index) {
        Server[] temp = new Server[servers.length - 1];
        if (index >= 0) System.arraycopy(servers, 0, temp, 0, index);
        if (temp.length - index >= 0) System.arraycopy(servers, index + 1, temp, index, temp.length - index);
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
        return FileIO.readFromFile("servers.txt").split("/");
    }

    public void setServerConnectionFrame(ServerConnectionFrame serverConnectionFrame) {
        this.serverConnectionFrame = serverConnectionFrame;
    }

}
