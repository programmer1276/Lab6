package Commands;

import ServerManagers.CommandManager;

import java.util.stream.Collectors;

public class Help extends Command {
    public Help(String name, String descr) {
        super(name, descr);
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 1) throw new ArrayIndexOutOfBoundsException("Здесь не должно быть аргументов");
        String result = CommandManager.getCommandsHashMap()
                .entrySet()
                .stream()
                .map(entry -> "    -" + entry.getKey() + ": " + entry.getValue().getDescr())
                .collect(Collectors.joining("\n", "Список доступных команд: \n", "\n"));

        return result;
    }
}