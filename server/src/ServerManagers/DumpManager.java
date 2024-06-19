package ServerManagers;

import Classes.*;
import Console.MyConsole;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DumpManager {

    private static String file = null;

    public static void setFile(String file) {
        DumpManager.file = file;
    }
    public static String getFile() {return DumpManager.file;}

    private static void checkFileExistance(String fileName) {
        File newFile = new File(fileName);
        if (newFile.exists()) {
            DumpManager.setFile(fileName);
        } else {
            try {
                // Если файл не существует, создаем его
                if (newFile.createNewFile()) {
                    DumpManager.setFile(fileName);
                    MyConsole.println("Новый файл " + fileName + " создан");
                } else {
                    MyConsole.println("Нельзя создать файл " + fileName);
                }
            } catch (IOException e) {
                MyConsole.println("Ошибка при создании " + fileName);
                e.printStackTrace();
            }
        }
    }

    public static void parseCollectionFromJson(String file) {
        checkFileExistance(file);
        JSONParser parser = new JSONParser();
        try {
            if (new File(file).length() == 0) {
                MyConsole.println("Файл пустой!");
                return;
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fileInputStream);
            JSONArray jsonArray = (JSONArray) (parser.parse(reader));
            for (Object obj : jsonArray) {
                JSONObject musicBandJsonObj = (JSONObject) (obj);
                long id = (Long) (musicBandJsonObj.get("id"));
                String name = (String) (musicBandJsonObj.get("name"));
                JSONObject coordsArr = (JSONObject) musicBandJsonObj.get("coordinates");
                long x = (Long) (coordsArr.get("x"));
                Double y = (Double) (coordsArr.get("y"));
                String dateString = (String) musicBandJsonObj.get("creationDate");
                LocalDate creationDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
                long numberOfParticipants = (Long) (musicBandJsonObj.get("numberOfParticipants"));
                String description = (String) (musicBandJsonObj.get("description"));
                String establishmentDateString = (String) musicBandJsonObj.get("establishmentDate");
                ZonedDateTime establishmentDate = ZonedDateTime.parse(establishmentDateString, DateTimeFormatter.ISO_ZONED_DATE_TIME);
                MusicGenre genre = MusicGenre.valueOf((String) musicBandJsonObj.get("genre"));
                JSONObject personDataArr = (JSONObject) musicBandJsonObj.get("frontMan");
                String personName = (String) (personDataArr.get("name"));
                String birthdayString = (String) personDataArr.get("birthday");
                ZonedDateTime birthdate = ZonedDateTime.parse(birthdayString, DateTimeFormatter.ISO_ZONED_DATE_TIME);
                long weight = (Long) (personDataArr.get("weight"));
                String passportID = (String) (personDataArr.get("passportID"));
                Country country = Country.valueOf((String) personDataArr.get("nationality"));

                Coordinates coordinates = new Coordinates(x, y);
                Person person = new Person(personName, birthdate, (int) weight, passportID, country);
                MusicBand musicBand = new MusicBand((int) id, name, coordinates, creationDate, numberOfParticipants, description, establishmentDate, genre, person);

                CollectionManager.addElementToCollection(musicBand);
            }
        } catch (ParseException | IOException e) {
            MyConsole.println("Что-то не так с файлом или нет соответствующих прав доступа!");
        }
    }

    private static void parseCollectionToJson(Collection<MusicBand> collection, String file) {
        if (CollectionManager.getCollection() == null) {
            CommandManager.setFlagOfCorrectArgument(false);
            MyConsole.println("Коллекция пуста, нельзя сохранить в файл!");
            return;
        }
        List<JSONObject> jsonObjects = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        for (MusicBand musicBand : collection) {
            JSONObject mb = new JSONObject();
            mb.put("id", musicBand.getID());
            mb.put("name", musicBand.getName());
            JSONObject coordArr = new JSONObject();
            coordArr.put("x", musicBand.getCoordinates().getX());
            coordArr.put("y", musicBand.getCoordinates().getY());
            mb.put("coordinates", coordArr);
            DateTimeFormatter formatter0 = DateTimeFormatter.ISO_LOCAL_DATE;
            LocalDate creationDate = musicBand.getCreationDate();
            String cd = creationDate.format(formatter0);
            mb.put("creationDate", cd);
            mb.put("numberOfParticipants", musicBand.getNumberOfParticipants());
            mb.put("description", musicBand.getDescription());
            DateTimeFormatter formatter1 = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            ZonedDateTime esteblishmentDate = musicBand.getEstablishmentDate();
            String ed = esteblishmentDate.format(formatter1);
            mb.put("establishmentDate", ed);
            MusicGenre genre = musicBand.getGenre();
            mb.put("genre", genre.toString());
            JSONObject frontMan = new JSONObject();
            frontMan.put("name", musicBand.getFrontMan().getName());
            DateTimeFormatter formatter2 = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            ZonedDateTime birthday = musicBand.getFrontMan().getBirthday();
            String birthdayToString = birthday.format(formatter2);
            frontMan.put("birthday", birthdayToString);
            frontMan.put("weight", musicBand.getFrontMan().getWeight());
            frontMan.put("passportID", musicBand.getFrontMan().getPassportID());
            Country country = musicBand.getFrontMan().getNationality();
            frontMan.put("nationality", country.toString());
            mb.put("frontMan", frontMan);
            jsonObjects.add(mb);
        }
        for (JSONObject musicBand : jsonObjects) {
            jsonArray.add(musicBand);
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonArray.toJSONString());
            writer.flush();
        } catch (IOException e) {
            CommandManager.setFlagOfCorrectArgument(false);
            MyConsole.println("Нет доступа к файлу или данный файл не существует!");
        }
    }

    public static void save() {
        String filePath = file;
        if (!filePath.endsWith("json")) throw new UnsupportedOperationException("Данный тип файла не поддерживается! Пожалуйста, введите команду save и в параметрах используйте файл типа .json");
        parseCollectionToJson(CollectionManager.getCollection(), filePath);
    }
}