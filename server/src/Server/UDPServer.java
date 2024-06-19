package Server;

import Managers.Requester;
import ServerManagers.CommandManager;
import Console.MyConsole;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;


public class UDPServer {
    private int port;
    private CommandManager commandExecutor;

    public UDPServer(int port, CommandManager commandExecutor) {
        this.port = port;
        this.commandExecutor = commandExecutor;
    }

    public void run() {
        try {
            DatagramChannel server = DatagramChannel.open();
            server.bind(new InetSocketAddress(port));
            server.configureBlocking(false);
            Selector selector = Selector.open();
            server.register(selector, SelectionKey.OP_READ);

            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(65508);
                        InetSocketAddress clientAddress = (InetSocketAddress) server.receive(buffer);
                        RequestHandler handler = new RequestHandler(buffer, clientAddress, commandExecutor);
                        RequestHandler.setRequestHandler(handler);
                        handler.run();
                        Requester response = handler.getResponse();
                        byte[] responseBytes = serializeObject(response);
                        DatagramPacket packet = new DatagramPacket(responseBytes, responseBytes.length, clientAddress);
                        ByteBuffer byteBuffer = convertDatagramPacketToByteBuffer(packet);
                        server.send(byteBuffer, clientAddress);
                    }
                    iterator.remove();
                }
            }
        } catch (BindException e) {
            MyConsole.println("Адрес уже используется!");
        } catch (IOException e) {
            MyConsole.println(e.getMessage());
        }
    }

    public static ByteBuffer convertDatagramPacketToByteBuffer(DatagramPacket packet) {
        // Извлечение данных из DatagramPacket
        byte[] data = packet.getData();
        int offset = packet.getOffset();
        int length = packet.getLength();

        // Создание ByteBuffer из данных
        ByteBuffer byteBuffer = ByteBuffer.wrap(data, offset, length);

        return byteBuffer;
    }


    private byte[] serializeObject(Requester commonRequester) throws IOException {
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)) {
            objectStream.writeObject(commonRequester);
            return byteStream.toByteArray();
        }
    }

}