package Commands;

import CommandBase.Command;
import Managers.Requester;
import Managers.InputHandler;
import ClientManagers.CommandManager;
import UDPClient.Client;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExecuteScript extends Command {
    public ExecuteScript(String name, String descr) {
        super(name, descr);
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 2)  throw new ArrayIndexOutOfBoundsException("Здесь должен быть один аргумент - путь до файла");
        try {
            Requester commonRequester = new Requester(getName(), executeScript(args[1]));
            Client.setCommonRequester(commonRequester);
        } catch (IOException e) {
            CommandManager.setFlagOfCorrectArgument(false);
            return "Такого файла не существует!";
        } catch (SecurityException e) {
            CommandManager.setFlagOfCorrectArgument(false);
            return "Доступ к файлу ограничен!";
        } catch (NoSuchElementException e) {
            CommandManager.setFlagOfCorrectArgument(false);
            InputHandler inputHandler = InputHandler.getInstance();
            inputHandler.setflagOfUserMode(true);
            return "EOF. Возвращение к user-mode";
        }
        return "";
    }

    public static boolean executeScript(String arg) throws IOException {
        InputHandler inputHandler = InputHandler.getInstance();
        String userInput = arg;
        if (checkRecursion(Path.of(userInput), new ArrayDeque<>())) throw new IOException("В скрипте найдена рекурсия");
        File file = new File(userInput);
        if (file.length() == 0) return false;
        inputHandler.setflagOfUserMode(false);
        inputHandler.setInputFromFile(file);
        return true;
    }


    private static boolean checkRecursion(Path path, ArrayDeque<Path> stack) throws IOException {
        if (stack.contains(path)) return true;
        stack.addLast(path);
        String str = Files.readString(path);
        Pattern pattern = Pattern.compile("execute_script .*");
        Matcher patternMatcher = pattern.matcher(str);
        while (patternMatcher.find()) {
            Path newPath = Path.of(patternMatcher.group().split(" ")[1]);
            if (checkRecursion(newPath, stack)) return true;
        }
        stack.removeLast();
        return false;
    }
}