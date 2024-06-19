package Commands;

import Classes.MusicBand;
import Server.RequestHandler;
import ServerManagers.CollectionManager;
import ServerManagers.CommandManager;

public class Update extends Command {

    public Update(String name, String descr) {
        super(name, descr);
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 2) throw new ArrayIndexOutOfBoundsException("Здесь должен быть ровно 1 аргумент - id элемента коллкции который вы хотите поменять");
        CommandManager.setFlagOfCorrectArgument(RequestHandler.getNewFlagOfCorrectArgument());
        if (!CommandManager.getFlagOfCorrectArgument()) {
            return "";
        }
        MusicBand updatedMusicBand = RequestHandler.getRequestHandler().getMusicBand();
        CollectionManager.removeById(updatedMusicBand.getID());
        CollectionManager.update(updatedMusicBand);
        return "Коллекция с id " + updatedMusicBand.getID() + " обновлена!";
    }
}