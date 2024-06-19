package Server;

import Classes.MusicBand;
import Managers.Requester;
import ServerManagers.CollectionManager;
import ServerManagers.CommandManager;
import Console.MyConsole;

import java.io.*;
import java.nio.ByteBuffer;
import java.net.InetSocketAddress;
public class RequestHandler implements Runnable {
    private ByteBuffer buffer;
    private InetSocketAddress clientAddress;
    private CommandManager commandManager;

    private static RequestHandler requestHandler;

    private String command;
    private MusicBand musicBand;
    private boolean result = true;


    private Requester commonRequester;

    private static boolean flagOfCorrectArgument = true;

    public RequestHandler(ByteBuffer buffer, InetSocketAddress clientAddress, CommandManager commandManager) {
        this.buffer = buffer;
        this.clientAddress = clientAddress;
        this.commandManager = commandManager;
    }

    @Override
    public void run() {
        try {
            CommandManager.buildCommands();
            buffer.flip();
            ObjectInputStream inStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            Requester commandAndObj = (Requester) inStream.readObject();

            if (commandAndObj != null) {
                setNewFlagOfCorrectArgument(commandAndObj.getFlagOfCorrectArgument());
                this.command = commandAndObj.getCommand();
                String print;
                if (command.equals("add") || command.startsWith("update")) {
                    this.musicBand = commandAndObj.getMusicBand();
                    print = "Поступило с клиента: " + command + ", " + musicBand;
                }
                else if (command.startsWith("execute_script")) {
                    this.result = commandAndObj.getCondition();
                    print = "Поступило с клиента: " + command + ", " + result;
                }
                else print = "Поступило с клиента: " + command;
                MyConsole.println(print);
                // Выполнение команды и подготовка ответа
                String serverResponse = CommandManager.executeCommand(command);
                Requester response = new Requester(serverResponse, CollectionManager.getPreviousIDs());
                this.commonRequester = response;
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
    public Requester getResponse() {
        return commonRequester;
    }

    public String getCommand() {
        return command;
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }

    public boolean getResult() {
        return result;
    }

    public static void setNewFlagOfCorrectArgument(boolean flagOfCorrectArgument) {
        RequestHandler.flagOfCorrectArgument = flagOfCorrectArgument;
    }

    public static void setRequestHandler(RequestHandler requestHandler) {
        RequestHandler.requestHandler = requestHandler;
    }
    public static RequestHandler getRequestHandler() {
        return requestHandler;
    }
    public static boolean getNewFlagOfCorrectArgument() {
        return flagOfCorrectArgument;
    }
}