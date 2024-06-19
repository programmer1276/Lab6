package Managers;

import Classes.MusicBand;
import java.io.Serializable;
import java.util.ArrayList;

public class Requester implements Serializable {
    private String command;
    private MusicBand musicBand;
    private String response;
    private ArrayList<Integer> ids;
    private boolean result = true;
    public Requester(String command, MusicBand musicBand) {
        this.command = command;
        this.musicBand = musicBand;
    }

    public Requester(String response, ArrayList<Integer> ids) {
        this.response = response;
        this.ids = ids;
    }

    public Requester(String command) {
        this.command = command;
    }

    public Requester(String command, boolean result) {
        this.command = command;
        this.result = result;
    }

    public String getCommand() {
        return command;
    }

    public boolean getCondition() {
        return result;
    }
    public MusicBand getMusicBand() {
        return musicBand;
    }

    public ArrayList<Integer> getIDs() {
        return ids;
    }

    public String getResponse() {
        return response;
    }
    public boolean getFlagOfCorrectArgument() {
        return result;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}