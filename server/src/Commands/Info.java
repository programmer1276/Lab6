package Commands;

import ServerManagers.CollectionManager;

public class Info extends Command {
    public Info(String name, String descr) {
        super(name, descr);
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 1) throw new ArrayIndexOutOfBoundsException("В параметрах функции нет элементов!");
        if (CollectionManager.getCollection() == null) {
            return "    -Тип коллекции: " + null
                    + "\n   -Количество элементов: " + 0 + " т.к. null"
                    + "\n   -Дата создания: " + CollectionManager.getCollectionInitialisationDate();
        } else {
            return "    -Тип коллекции: " + CollectionManager.getCollection().getClass().toString()
                    + "\n   -Количество элементов: " + CollectionManager.getCollection().size()
                    + "\n   -Дата создания: " + CollectionManager.getCollectionInitialisationDate();
        }
    }
}