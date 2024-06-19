package Commands;

import Classes.MusicBand;
import ClientManagers.CommandManager;
import Managers.Requester;
import ClientManagers.MusicBandRequester;
import CommandBase.*;
import UDPClient.Client;

public class Update extends Command {

    public Update(String name, String descr) {
        super(name, descr);
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 2) throw new ArrayIndexOutOfBoundsException("Здесь должен быть ровно 1 аргумент - id элемента коллкции который вы хотите поменять");
        try {
            MusicBandRequester musicBandRequester = new MusicBandRequester();
            int id = Integer.parseInt(args[1]);
            if (Client.getIDs().contains(id)) {
                MusicBand updatedBand = musicBandRequester.requestUserBand();
                updatedBand.setId(id);
                Requester commonRequester = new Requester(args[0] + " " + args[1], updatedBand);
                Client.setCommonRequester(commonRequester);
                return "Обновленная коллекция собрана!";
            } else {
                CommandManager.setUnexArg(true);
                return "Элемент коллекции с данным id не существует в коллекции";
            }
        } catch (NumberFormatException e) {
            CommandManager.setUnexArg(true);
            return "Неверный формат ввода id. Введите пожалуйста число(тип int)";
        }
    }
}
