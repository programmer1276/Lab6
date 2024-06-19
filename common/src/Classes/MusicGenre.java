package Classes;

import Console.MyConsole;

import java.io.Serializable;

public enum MusicGenre implements Serializable {
    PSYCHEDELIC_ROCK("PSYCHEDELIC_ROCK"),
    HIP_HOP("HIP_HOP"),
    SOUL("SOUL"),
    BLUES("BLUES"),
    POST_ROCK("POST_ROCK");

    final String name;
    MusicGenre(String name) {
        this.name = name;
    }

    public static void showGenres() {
        int counter = 1;
        for (MusicGenre i: MusicGenre.values()) {
            MyConsole.println("" + counter++ + ". - " + i);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}