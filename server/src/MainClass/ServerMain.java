package MainClass;

import ServerManagers.CommandManager;
import Server.UDPServer;
import ServerManagers.DumpManager;

public class ServerMain {
    public static void main (String[] args) {
         //Загружаем данные в CollectionManager с помощью DumpManager
        if (!args[0].endsWith(".json")) args[0] = args[0] + ".json";
        if (args.length != 1) throw new ArrayIndexOutOfBoundsException("Здесь должен быть только один параметр - имя файла, с которым будет происходить дальнейшая работа программы");
        DumpManager.setFile(args[0]);
        DumpManager.parseCollectionFromJson(args[0]);
//        DumpManager.setFile("test.json");
//        DumpManager.parseCollectionFromJson("test.json");

        // Инициализируем CommandExecutor
        CommandManager commandExecutor = new CommandManager();
        UDPServer server = new UDPServer(8000, commandExecutor);

        server.run();
    }
}