package Commands;

import Console.MyConsole;
import ServerManagers.CommandManager;
import ServerManagers.DumpManager;

public class Exit extends Command {
    public Exit(String name, String descr) {
        super(name, descr);
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 1) throw new ArrayIndexOutOfBoundsException("У данной команды не должно быть параметров!");
        try {
            DumpManager.save();
            String print;
            print = CommandManager.getFlagOfCorrectArgument() ? "Клиент завершил работу, коллекция сохранена в файл " + DumpManager.getFile() : "";
            MyConsole.println(print);
            return "";
        } catch (UnsupportedOperationException e) {
            MyConsole.println(e.getMessage());
            return "";
        }
    }
}