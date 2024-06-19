package Commands;

import ServerManagers.CollectionManager;

public class Clear extends Command {
    public Clear(String name, String descr) {
        super(name, descr);
    }

    @Override
    public String execute(String[] args){
        if (args.length != 1) throw new ArrayIndexOutOfBoundsException("Здесь не должно быть аргументов!");
        boolean result = CollectionManager.clear();
        if (result) {
            return "\n--------------------------\nКоллекция была очищена\n";
        } else return "Воспользуйтесь командой \\\"add\\\" чтобы добавить элемент в коллекцию";
    }
}
