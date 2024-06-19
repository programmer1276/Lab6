package Commands;

import java.util.Arrays;

import Console.MyConsole;
import Exceptions.EmptyCollectionException;
import ServerManagers.CollectionManager;
import ServerManagers.CommandManager;
import java.util.stream.Collectors;


public class Show extends Command {
    public Show(String name, String descr) {
        super(name, descr);
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 1) throw new ArrayIndexOutOfBoundsException("Здесь не должно быть аргументов");
        try {
            if (CollectionManager.show()) {
                String response = CollectionManager.getCollection().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n--------------------------\n", "", "\n"));

                return response;
            } else {
                return "В коллекции пусто! Заполните ее чем-нибудь";
            }
        } catch (NullPointerException e) {
            return e.getMessage();
        }
    }
}