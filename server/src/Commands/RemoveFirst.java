package Commands;

import ServerManagers.CollectionManager;

import java.util.NoSuchElementException;

public class RemoveFirst extends Command{

    public RemoveFirst(String name, String descr) {
        super(name, descr);
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 1) throw new ArrayIndexOutOfBoundsException("Здесь не должно быть аргументов!");
        try {
            boolean result = CollectionManager.removeFirst();
            if (result) {
                return "--------------------------\n" + "Первый элемент коллекции был удален!";
            } else return "Коллекция пуста, нельзя удалить первый элемент";
        } catch (NoSuchElementException e){
            return e.getMessage();
        }
    }
}