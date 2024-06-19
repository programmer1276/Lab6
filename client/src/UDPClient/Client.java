package UDPClient;

import ClientManagers.CommandManager;
import Managers.Requester;
import Managers.InputHandler;
import Console.MyConsole;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client {
    private String serverHost;
    private int serverPort;
    private final InputHandler inputHandler;
    private DatagramSocket socket;
    private InetSocketAddress address;
    private static ArrayList<Integer> previousIds = new ArrayList<>();

    private static Requester commonRequester;

    public Client(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.inputHandler = InputHandler.getInstance();
    }

    public void startClient() {
        try {
            address = new InetSocketAddress(serverHost, serverPort);
            socket = new DatagramSocket();
            CommandManager.buildCommands();
            CommandManager.executeCommand("show");
            sendCommand(new Requester("show"));
            Requester firstResponce = receiveResponse();
            if (firstResponce != null) {
                String response = firstResponce.getResponse();
                MyConsole.println("Connected to server.");
                MyConsole.println(response);
                previousIds = firstResponce.getIDs();
            }
            work(inputHandler);

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendCommand(Requester command) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(command);
        byte[] bytes = baos.toByteArray();
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address.getAddress(), serverPort);
        socket.send(packet);
    }

    private Requester receiveResponse() throws IOException, ClassNotFoundException {
        try {
            socket.setSoTimeout(250);
            byte[] buffer = new byte[65508];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Requester) ois.readObject();
        } catch (SocketTimeoutException e) {
            if (inputHandler.getInput() != null && inputHandler.getInput().equals("exit")) {
                System.exit(0);
            }
            MyConsole.println("Сервер отключился, потеряно соединение! Пытаемся переподключиться");
            return null;
        }
    }


    public void work(InputHandler inputHandler) throws IOException, ClassNotFoundException {
        while (true) {
            CommandManager.setUnexArg(false);
            MyConsole.println("Введите команду: ");
            inputHandler.setInputFromConsole();
            String userInput = inputHandler.getInput();
            if (!CommandManager.executeCommand(userInput) && !CommandManager.getUnexArg()) {
                sendCommand(new Requester(userInput));
            } else if (CommandManager.getUnexArg()) {
                continue;
            } else {
                sendCommand(commonRequester);
            }
            Requester commonResponce = receiveResponse();
            if (commonResponce != null) {
                String serverResponse = commonResponce.getResponse();
                previousIds = commonResponce.getIDs();
                MyConsole.println(serverResponse);
            } else {
                continue;
            }
            if (userInput.equals("exit")) {
                System.exit(0);
            }
        }
    }

    public static void setCommonRequester(Requester commonRequester) {
        Client.commonRequester = commonRequester;
    }

    public static ArrayList<Integer> getIDs() {
        return Client.previousIds;
    }
}