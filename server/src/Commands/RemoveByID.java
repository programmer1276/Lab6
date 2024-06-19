package Commands;

import ServerManagers.CollectionManager;
import ServerManagers.CommandManager;

import java.util.NoSuchElementException;

public class RemoveByID extends Command {

    public RemoveByID(String name, String descr) {
        super(name, descr);
    }

    @Override
    public String execute(String[] args){
        if (args.length != 2) throw new ArrayIndexOutOfBoundsException("Здесь должен быть только 1 аргумент! (тип: int)");
        try {
            int id = Integer.parseInt(args[1]);
            boolean result = CollectionManager.removeById(id);
            if (result) {
                return "--------------------------\nМузыкальная группа с id " + id + " успешно была удалена из коллекции\n";
            }
        } catch (NoSuchElementException e){
            CommandManager.setFlagOfCorrectArgument(false);
            return "В коллекции нет элемента с данным индексом";
        }
        return "";
    }
}