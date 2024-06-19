package Classes;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private ZonedDateTime birthday; //Поле не может быть null
    private Integer weight; //Поле может быть null, Значение поля должно быть больше 0
    private String passportID; //Длина строки не должна быть больше 42, Длина строки должна быть не меньше 4, Поле может быть null
    private Country nationality; //Поле не может быть null

    public Person(String name, ZonedDateTime birthday, Integer weight, String passportID, Country nationality) {
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
        this.passportID = passportID;
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "person{name: " + name + ", birthday: " + birthday + ", weight: " + weight + ", passportID: " + passportID +
                ", nationality: " + nationality + "}";
    }

//    @Override
//    public boolean validityCheck() {
//        if (name == null || name.isEmpty()) return false;
//        if (birthday == null || nationality == null) return false;
//        if (weight <= 0) return false;
//        if (passportID.length() > 42 || passportID.length() < 4) return false;
//        return true;
//    }

    public void setName(String name) {
        this.name = name;
    }
    public void setBirthDate(ZonedDateTime birthday) {
        this.birthday = birthday;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }
    public void setNationality(String nationality) {
        this.nationality = Country.valueOf(nationality);
    }
    public String getName() {return name;}
    public ZonedDateTime getBirthday() {return birthday;}
    public Integer getWeight() {
        return weight;
    }
    public String getPassportID() {return passportID;}
    public Country getNationality() {return nationality;}
}
