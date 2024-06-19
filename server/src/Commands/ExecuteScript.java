package Commands;

import Server.RequestHandler;

public class ExecuteScript extends Command {
    public ExecuteScript(String name, String descr) {
        super(name, descr);
    }

    @Override
    public String execute(String[] args) {
        boolean result = RequestHandler.getRequestHandler().getResult();
            if (!result) {
                return "Файл пустой!";
            } else {
                return "\n--------------------------\nЗапускаем скрипт...\n";
            }
    }
}