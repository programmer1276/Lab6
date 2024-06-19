package Commands;

import Server.RequestHandler;
import ServerManagers.CollectionManager;

public class Add extends Command {
    public Add(String name, String descr) {
        super(name, descr);
    }
    @Override
    public String execute(String[] args) {
        if (args.length != 1) throw new ArrayIndexOutOfBoundsException("Здесь не должно быть аргументов!");
        boolean result = CollectionManager.add(RequestHandler.getRequestHandler().getMusicBand());
        if (result) {
            return "\n" + "--------------------------\n" + "Новая музыкальная группа добавлена в коллекцию.\n";
        } else return "Исправьте скрипт и проверьте, чтобы все передаваемые значения были валидны";
    }
}