package Commands;

import Classes.MusicBand;
import ClientManagers.MusicBandRequester;
import Managers.Requester;
import Managers.InputHandler;
import CommandBase.*;
import UDPClient.Client;

public class Add extends Command {
    public Add(String name, String descr) {
        super(name, descr);
    }
    @Override
    public String execute(String[] args) {
        if (args.length != 1) throw new ArrayIndexOutOfBoundsException("Здесь не должно быть аргументов!");
        MusicBandRequester musicBandRequester = new MusicBandRequester();
        MusicBand newMusicBand = null;
        InputHandler inputHandler = InputHandler.getInstance();
        if (inputHandler.getflagOfUserMode()) {
            newMusicBand = musicBandRequester.requestUserBand();
        } else {
            newMusicBand = musicBandRequester.requestNonUserBand();
        }
        Requester commonRequester = new Requester(getName(), newMusicBand);
        Client.setCommonRequester(commonRequester);
        return "";
    }
}