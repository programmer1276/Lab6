package Classes;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class MusicBand implements Comparable<MusicBand>, Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long numberOfParticipants; //Значение поля должно быть больше 0
    private String description; //Поле не может быть null
    private ZonedDateTime establishmentDate; //Поле может быть null
    private MusicGenre genre; //Поле может быть null
    private Person frontMan; //Поле не может быть null

    public MusicBand(int id, String name, Coordinates coordinates, LocalDate creationDate, long numberOfParticipants,
                     String description, ZonedDateTime establishmentDate, MusicGenre genre, Person frontMan) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberOfParticipants = numberOfParticipants;
        this.description = description;
        this.establishmentDate = establishmentDate;
        this.genre = genre;
        this.frontMan = frontMan;
    }

    @Override
    public String toString() {
        return "musicBand{id: "+ id + ", name: " + name + ", coordinates: " + coordinates + ", creationDate: " + creationDate +
                ", numberOfParticipants: " + numberOfParticipants + ", description: " + description +
                ", establishmentDate: " + establishmentDate + ", genre: " + genre + ", frontMan: " + frontMan + "}";
    }

    public int getID() {
        return id;
    }

    @Override
    public int compareTo(MusicBand oMusicBand){
        return this.id - oMusicBand.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public long getNumberOfParticipants() {
        return numberOfParticipants;
    }
    public ZonedDateTime getEstablishmentDate() {
        return establishmentDate;
    }
    public String getDescription() {
        return description;
    }
    public MusicGenre getGenre() {
        return genre;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public LocalDate getCreationDate() {return creationDate;}
    public Person getFrontMan() {return frontMan;}

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setNumberOfParticipants(long numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public void setEstablishmentDate(ZonedDateTime establishmentDate) {
        this.establishmentDate = establishmentDate;
    }
    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }
    public void setPerson(Person frontMan) {
        this.frontMan = frontMan;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}