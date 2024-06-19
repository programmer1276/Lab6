package MainClass;

import UDPClient.Client;

public class ClientMain {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8000;

    public static void main(String[] args) {
        Client client = new Client(SERVER_HOST, SERVER_PORT);
        client.startClient();
    }
}