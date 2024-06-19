package Commands;

import ServerManagers.CollectionManager;

import java.util.List;
import java.util.stream.Collectors;

public class FilterStartsWithName extends Command {
    public FilterStartsWithName(String name, String descr) {
        super(name, descr);
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 2)
            throw new ArrayIndexOutOfBoundsException("Здесь должен быть 1 элемент - имя элемента вхождение которого вы хотите проверить!");
        boolean condition = CollectionManager.filterStartsWithName(args[1]);
        List<String> result;
        if (condition) {
            result = CollectionManager.getElementToFind().stream()
                    .filter(element -> element.getName().startsWith(args[1]))
                    .map(element -> "Элемент с ID " + element.getID() + ": " + element.getName())
                    .collect(Collectors.toList());
        } else return "Элементы с именем, начинающимся на заданную подстроку не найдено";
        CollectionManager.getElementToFind().clear();
        return String.join("\n", result);
    }
}
