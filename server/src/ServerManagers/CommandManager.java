package ServerManagers;

import Commands.*;
import Exceptions.EmptyCollectionException;
import Exceptions.InvalidCommandException;

import java.util.HashMap;

public class CommandManager {
    /**
     * hashMap to save all commands
     */
    static HashMap<String, Command> commands = new HashMap<>();
    /**
     * flag to fixate validity of user input from console
     */
    private static boolean flagOfCorrectArgument = true;
    public static void buildCommands() {
        Help help = new Help("help", "вывести справку по доступным командам");
        Add add = new Add("add", "add {element} добавить новый элемент в коллекцию");
        History history = new History("history", "вывести 15 последних выполненных команд");
        Exit exit = new Exit("exit", "завершить программу без сохранения в файл");
        Clear clear = new Clear("clear", "Очистить коллекцию");
        Info info = new Info("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        ExecuteScript executeScript = new ExecuteScript("execute_script", "execute_script {file_name} считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        Show show = new Show("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        RemoveByID removeByID = new RemoveByID("remove_by_id", "remove_by_id {id} удалить элемент коллекции по его id");
        Reorder reorder = new Reorder("reorder", "отсортировать коллекцию в порядке, обратном нынешнему");
        RemoveLast removeLast = new RemoveLast("remove_last", "удалить последний элемент коллекции");
        FilterStartsWithName filterStartsWithName = new FilterStartsWithName("filter_starts_with_name", "filter_starts_with_name {name} вывести элементы, значение поля name которых начинается с заданной подстроки");
        RemoveFirst removeFirst = new RemoveFirst("remove_first", "удалить первый элемент из колекции");
        Update update = new Update("update", "update {id} обновить значение элемента коллекции, id которого равен заданному");

        commands.put(help.getName(), help);
        commands.put(add.getName(), add);
        commands.put(history.getName(), history);
        commands.put(exit.getName(), exit);
        commands.put(clear.getName(), clear);
        commands.put(info.getName(), info);
        commands.put(executeScript.getName(), executeScript);
        commands.put(show.getName(), show);
        commands.put(removeByID.getName(), removeByID);
        commands.put(reorder.getName(), reorder);
        commands.put(removeLast.getName(), removeLast);
        commands.put(filterStartsWithName.getName(), filterStartsWithName);
        commands.put(removeFirst.getName(), removeFirst);
        commands.put(update.getName(), update);
    }

    public static void setFlagOfCorrectArgument(boolean flagOfCorrectArgument) {
        CommandManager.flagOfCorrectArgument = flagOfCorrectArgument;
    }
    public static boolean getFlagOfCorrectArgument() {
        return CommandManager.flagOfCorrectArgument;
    }

    public static String executeCommand(String command) throws InvalidCommandException {
        if (command != null) {
            String[] commandAndArgs = command.split(" ", 2);
            try {
                String response = commands.get(commandAndArgs[0]).execute(commandAndArgs);
                addCommandToHistory(command);
                flagOfCorrectArgument = true;
                return response;
            } catch (InvalidCommandException | ArrayIndexOutOfBoundsException | EmptyCollectionException e) {
                return e.getMessage();
            }
        }
        return "";
    }

    public static HashMap<String, Command> getCommandsHashMap() {
        return commands;
    }

    private static int headIndex = -1;
    private static int tailIndex = -1;
    private static final int HISTORY_SIZE = 15;
    final static String[] commandHistory = new String[HISTORY_SIZE];

    private static void addCommandToHistory(String st){
        String[] arr = st.split(" ");
        if (headIndex == -1) {
            headIndex = 0;
            tailIndex = 0;

            if (arr.length == 2) {
                commandHistory[headIndex] = (flagOfCorrectArgument) ? arr[0] + " " + arr[1] : arr[0] + " " + arr[1] + "(неверный аргумент!)";
            } else {
                commandHistory[headIndex] = arr[0];
            }

        } else {
            tailIndex = (tailIndex + 1) % HISTORY_SIZE;
            if (arr.length == 2) {
                commandHistory[tailIndex] = (flagOfCorrectArgument) ? arr[0] + " " + arr[1] : arr[0] + " " + arr[1] + "(неверный аргумент!)";
            } else {
                commandHistory[tailIndex] = arr[0];
            }
            if (tailIndex == headIndex) {
                headIndex = (headIndex + 1) % HISTORY_SIZE;
            }
        }
    }

    public static String history() {
        StringBuilder history = new StringBuilder();
        history.append("Ваша история команд: \n----------------------------------\n");
        if (headIndex == -1) {
            history.append("История команд пуста");
            return history.toString();
        } else {
            int i = headIndex;
            int count = 1;
            do {
                history.append(count + ": " + commandHistory[i] + "\n");
                i = (i + 1) % HISTORY_SIZE;
                count++;
            } while (i != tailIndex + 1);
        }
        return history.toString();
    }
}
