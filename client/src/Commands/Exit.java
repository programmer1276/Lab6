package Commands;

import CommandBase.Command;
import Managers.Requester;
import UDPClient.Client;

public class Exit extends Command {
    public Exit(String name, String descr) {
        super(name, descr);
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 1) throw new ArrayIndexOutOfBoundsException("У данной команды не должно быть параметров!");
        Requester commonRequester = new Requester("exit");
        Client.setCommonRequester(commonRequester);
        return "Спасибо за работу с нашей пограммой. До свидания и хорошего дня!";
    }
}
