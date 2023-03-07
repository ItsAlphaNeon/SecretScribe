package controller;

public class Server {
    private String name;
    private String ip;
    private int port;

    public Server(String name, String ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public Server(String serverIP) {
        this.ip = serverIP;
        // determine if port is specified, if not use the port the ip is pointing to
        // TODO: implement this
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
}
