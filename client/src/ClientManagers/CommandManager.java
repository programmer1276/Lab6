package ClientManagers;

import CommandBase.Command;
import Commands.Add;
import Commands.ExecuteScript;
import Commands.Exit;
import Commands.Update;
import Console.MyConsole;
import Exceptions.EmptyCollectionException;
import Exceptions.InvalidCommandException;

import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.HashMap;

public class CommandManager {
    /**
     * hashMap to save all commands
     */
    static HashMap<String, Command> commands = new HashMap<>();
    /**
     * flag to fixate validity of user input from console
     */
    private static boolean flagOfCorrectArgument;

    private static ArrayList<String> allCommands = new ArrayList<>();
    private static boolean unexpectedArg;

    public static void buildCommands() {
        Add add = new Add("add", "add {element} добавить новый элемент в коллекцию");
        Exit exit = new Exit("exit", "завершить программу без сохранения в файл");
        Update update = new Update("update", "update {id} обновить значение элемента коллекции, id которого равен заданному");
        ExecuteScript executeScript = new ExecuteScript("execute_script", "execute_script {file_name} считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");

        commands.put(add.getName(), add);
        commands.put(exit.getName(), exit);
        commands.put(update.getName(), update);
        commands.put(executeScript.getName(), executeScript);
        allCommands.add("add");
        allCommands.add("clear");
        allCommands.add("execute_script");
        allCommands.add("exit");
        allCommands.add("filter_starts_with_name");
        allCommands.add("help");
        allCommands.add("history");
        allCommands.add("info");
        allCommands.add("remove_by_id");
        allCommands.add("remove_first");
        allCommands.add("remove_last");
        allCommands.add("reorder");
        allCommands.add("show");
        allCommands.add("update");
    }

    public static void setFlagOfCorrectArgument(boolean flagOfCorrectArgument) {
        CommandManager.flagOfCorrectArgument = flagOfCorrectArgument;
    }

    public static boolean getUnexArg() {
        return unexpectedArg;
    }
    public static void setUnexArg(boolean unexArg) {
        unexpectedArg = unexArg;
    }

    public static boolean executeCommand(String command) throws InvalidCommandException {
        if (command != null && !command.equals("")) {
            String[] commandAndArgs = command.split(" ", 2);
            if (!allCommands.contains(commandAndArgs[0])) {
                MyConsole.println("Программа не поддерживает данную команду, попробуйте еще раз");
                unexpectedArg = true;
                return false;
            }
            try {
                if (!(commands.containsKey(commandAndArgs[0]))) {
                    return false;
                }
                MyConsole.println(commands.get(commandAndArgs[0]).execute(commandAndArgs));
                flagOfCorrectArgument = true;
                return true;
            } catch (InvalidCommandException | ArrayIndexOutOfBoundsException | EmptyCollectionException e) {
                unexpectedArg = true;
                MyConsole.println(e.getMessage());
            }
        }
        unexpectedArg = true;
        return false;
    }
}