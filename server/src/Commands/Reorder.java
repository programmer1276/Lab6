package Commands;

import Console.MyConsole;
import ServerManagers.CollectionManager;

public class Reorder extends Command {
    public Reorder(String name, String descr) {
        super(name, descr);
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 1) throw new ArrayIndexOutOfBoundsException("Здесь не должно быть аргументов!");
        StringBuilder response = new StringBuilder();
        response.append("Коллекция перед сортировкой: " + CollectionManager.getCollection() + "\n");
        boolean result = CollectionManager.reorder();
        if (result) {
            MyConsole.println("Коллекция после сортировки: " + CollectionManager.getCollection());
            return response.append("Коллекция после сортировки: " + CollectionManager.getCollection()).toString();
        } else return "Коллекция null. Нельзя воспользоваться сортировкой";
    }
}