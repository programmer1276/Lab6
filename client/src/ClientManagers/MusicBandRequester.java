package ClientManagers;

import Builders.CoordinatesBuilder;
import Builders.MusicBandBuilder;
import Builders.PersonBuilder;
import Classes.Coordinates;
import Classes.MusicBand;
import Classes.MusicGenre;
import Classes.Person;
import Console.MyConsole;
import Exceptions.EmptyLineException;
import Exceptions.IncorrectInputException;
import Managers.InputHandler;
import Validators.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class MusicBandRequester {
    MusicBandBuilder musicBandBuilder = new MusicBandBuilder();
    NameValidator nameValidator = new NameValidator();
    WeightValidator weightValidator = new WeightValidator();
    PassportIdValidator passportIdValidator = new PassportIdValidator();
    NumberOfParticipantsValidator numberOfParticipantsValidator = new NumberOfParticipantsValidator();
    /**
     * indicates validity of user input in method requestNonUserBand
     */
    boolean validArguments = true;

    /**
     * requests input of fields from user
     * @return
     */
    public MusicBand requestUserBand() {
        InputHandler inputHandler = InputHandler.getInstance();
        MusicBand newBand = musicBandBuilder.build();

        //имя
        requestUserCollectionStringName(newBand, inputHandler, "Введите название музыкальной группы которую хотите добавить в коллекцию:", nameValidator, "Имя было добавлено.", "Некорректный ввод. Попробуйте снова", "Имя не может быть пустым.");

        //описание музыкальной группы
        MyConsole.println();
        requestUserMusicBandDescription(newBand, inputHandler, "Введите описание музыкальной группы:", "Описание успешно изменено", "Некорректный ввод. Попробуйте снова");

        //coordinates
        MyConsole.println();
        CoordinatesBuilder coordinatesBuilder = new CoordinatesBuilder();
        Coordinates coordinates = coordinatesBuilder.build();
        newBand.setCoordinates(coordinates);

        //number of participants
        MyConsole.println();
        requestUserNumberOfParticipants(newBand, inputHandler, "Введите количество участников (int).", numberOfParticipantsValidator, "Количество участников добавлено.", "Введено число неверного типа либо введено вообще не число! Пожалуйста, введите число еще раз", "количество должно быть >= 0");

        //задание жанра
        MyConsole.println();
        newBand.setGenre(requestUserGenre(inputHandler, "Введите номер музыкального жанра.", "Жанр был добавлен", "Жанр под данным номером не существует! Пожалуйста попробуйте еще раз", "Введено число неверного типа либо введено вообще не число! Пожалуйста, введите число еще раз"));


        //establishmentDate
        MyConsole.println();
        requestUserEstablishmentDate(newBand, inputHandler, "Задайте дату создания(esteblishment date) \"yyyy-MM-dd\" - в данном формате.", "Дата создания(esteblishment date) обновлена!", "Неверный формат даты. Введите дату в формате yyyy-MM-dd");

        //добавление посетителя
        MyConsole.println();
        PersonBuilder personBuilder = new PersonBuilder();
        Person person = personBuilder.build();
        newBand.setPerson(person);
        return newBand;
    }

    /**
     * requests fields from file in non user mode
     * @return
     */
    public MusicBand requestNonUserBand() {
        MusicBand nonUserBand = musicBandBuilder.build();
        InputHandler inputHandler = InputHandler.getInstance();

        //name
        requestNonUserStringName(inputHandler, nonUserBand);

        //описание музыкальной группы
        requestNonUserDescription(inputHandler, nonUserBand);

        //coordinates
        requestNonUserCoords(inputHandler, nonUserBand);

        //numberOfParticipants
        requestNonUserNumberOfParticipants(inputHandler, nonUserBand);

        //establishmentDate
        requestNonUserEstablishmentDate(inputHandler, nonUserBand);

        //musicGenre
        requestNoneUserMusicGenre(inputHandler, nonUserBand);

        Person frontMan = new Person(null, null, 0, null, null);
        //имя
        requestNonUserFrontManName(inputHandler, nameValidator, frontMan);

        //дата рождения
        requestNonUserBirthDate(inputHandler, frontMan);

        //вес
        requestNonUserWeight(inputHandler, weightValidator, frontMan);

        //ID пасспорта
        requestNonUserPassportId(inputHandler, passportIdValidator, frontMan);

        //национальность
        requestNonUserCountry(inputHandler, frontMan);

        nonUserBand.setPerson(frontMan);

        //result
        if (validArguments) {
            MyConsole.println("Все аргументы валидны. Новая музыкальная группа будет добавлена в коллекцию.");
        }
        else {
            MyConsole.println("Не все аргументы валидны! Проверьте свой скрипт на валидность команд и всех передаваемых параметров.");
            return null;
        }
        return nonUserBand;
    }

    private void requestNonUserStringName(InputHandler inputHandler, MusicBand nonUserBand) {
        inputHandler.setInputFromConsole();
        nonUserBand.setName(inputHandler.getInput());
        MyConsole.println("Имя: " + nonUserBand.getName());
        MyConsole.println();
    }

    private void requestNonUserDescription(InputHandler inputHandler, MusicBand nonUserBand) {
        inputHandler.setInputFromConsole();
        nonUserBand.setDescription(inputHandler.getInput());
        MyConsole.println("Описание музыкальной группы: " + nonUserBand.getDescription());
        MyConsole.println();
    }

    private void requestNonUserCoords(InputHandler inputHandler, MusicBand nonUserBand) {
        Coordinates coordinates = new Coordinates(0, 0d);
        try {
            inputHandler.setInputFromConsole();
            coordinates.setX(inputHandler.getLongInput());
            inputHandler.setInputFromConsole();
            coordinates.setY(inputHandler.getDoubleInput());
        } catch (NumberFormatException | EmptyLineException e) {
            validArguments = false;
            MyConsole.println("! Координаты не были добавлены. Невалидные аргументы !");
        }
        nonUserBand.setCoordinates(coordinates);
        MyConsole.println("X: "+ coordinates.getX() + "; Y: " + coordinates.getY());
        MyConsole.println();
    }

    private void requestNonUserNumberOfParticipants(InputHandler inputHandler, MusicBand nonUserBand) {
        try {
            inputHandler.setInputFromConsole();
            int number = inputHandler.getIntInput();
            nonUserBand.setNumberOfParticipants(number);
            MyConsole.println("Количество участников: " + nonUserBand.getNumberOfParticipants());
            MyConsole.println();
        } catch (NumberFormatException e) {
            validArguments = false;
            MyConsole.println("! Количество участников не было добавленно. Невалидные аргументы !");
        }
    }

    private void requestNonUserEstablishmentDate(InputHandler inputHandler, MusicBand nonUserBand) {
        try {
            inputHandler.setInputFromConsole();
            nonUserBand.setEstablishmentDate(inputHandler.getDateInput());
        } catch (DateTimeParseException | EmptyLineException e) {
            validArguments = false;
            MyConsole.println("! Дата основания не добавлена. Невалидные аргументы !");
        }
        MyConsole.println("Дата основания: " + nonUserBand.getEstablishmentDate());
        MyConsole.println();
    }

    private void requestNoneUserMusicGenre(InputHandler inputHandler, MusicBand nonUserBand) {
        MusicGenre[] array = MusicGenre.values();
        try {
            inputHandler.setInputFromConsole();
            MusicGenre resultingGenre = array[inputHandler.getIntInput()-1];
            nonUserBand.setGenre(resultingGenre);
        } catch (NumberFormatException e) {
            validArguments = false;
            MyConsole.println("! Музыкальный жанр не был добавлен! Невалидные аргументы !");
        }
        if (nonUserBand.getGenre() == null){
            validArguments = false;
            MyConsole.println("! Музыкальный жанр не был добавлен !");
        }
        MyConsole.println("Музыкальный жанр: " + nonUserBand.getGenre());
        MyConsole.println();
    }

    private void requestNonUserFrontManName(InputHandler inputHandler, Validatable validator, Person frontMan) {
        try {
            inputHandler.setInputFromConsole();
            String userInput = inputHandler.getInput();
            if(validator.validate(userInput)){
                frontMan.setName(userInput);
                MyConsole.println("Имя было добавлено!");
            }
            else{
                MyConsole.println("Имя не может быть пустым!");
            }
        } catch (IncorrectInputException e) {
            MyConsole.println("Некорректный ввод!");
        }
        MyConsole.println();
    }

    private void requestNonUserBirthDate(InputHandler inputHandler, Person frontMan) {
        try {
            inputHandler.setInputFromConsole();
            ZonedDateTime birthDate = inputHandler.getDateInput();
            if (birthDate != null) {
                frontMan.setBirthDate(birthDate);
                MyConsole.println("Дата рождения изменена успешно!");
            }
        } catch (DateTimeParseException e) {
            validArguments = false;
            MyConsole.println("Некорректный ввод! Неверный формат");
        }
        MyConsole.println();
    }

    private void requestNonUserWeight(InputHandler inputHandler, Validatable validator, Person frontMan) {
        try {
            inputHandler.setInputFromConsole();
            Integer weight = inputHandler.getIntInput();
            if (validator.validate(weight)) {
                frontMan.setWeight(weight);
                MyConsole.println("Вес успешно изменен!");
            }
            else {
                validArguments = false;
                MyConsole.println("Вес не может быть отрицательным!");
            }
        } catch (NumberFormatException e) {
            validArguments = false;
            MyConsole.println("Некорректный ввод! Неверный формат(ожидаемый тип - int)");
        }
        MyConsole.println();
    }

    private void requestNonUserPassportId(InputHandler inputHandler, Validatable validator, Person frontMan) {
        try {
            inputHandler.setInputFromConsole();
            String userInput = inputHandler.getInput();
            if (validator.validate(userInput)) {
                frontMan.setPassportID(userInput);
                MyConsole.println("ID пасспорта успешно изменен");
            }
            else {
                validArguments = false;
                MyConsole.println("Длина ID пасспорта должна быть >= 4 и <= 42");
            }
        } catch (IncorrectInputException e) {
            validArguments = false;
            MyConsole.println("Некорректный ввод!");
        }
        MyConsole.println();
    }

    private void requestNonUserCountry(InputHandler inputHandler, Person frontMan) {
        try {
            inputHandler.setCountryInputFromConsole();
            String userInput = inputHandler.getCountryInput();
            if (userInput != null) {
                frontMan.setNationality(userInput);
                MyConsole.println("Национальность успешно изменена!");
            }
        } catch (IncorrectInputException e) {
            validArguments = false;
            MyConsole.println("Некорректный ввод!");
        }
    }
    public void requestUserCollectionStringName(
            MusicBand newBand,
            InputHandler inputHandler,
            String inputMessage,
            Validatable validator,
            String successMessage,
            String errorMessage,
            String validateMessage
    ) {
        while(true) {
            MyConsole.println(inputMessage);
            try {
                inputHandler.setInputFromConsole();
                String userInput = inputHandler.getInput();
                if (userInput.trim().equals("")) {
                    continue;
                }
                if (validator.validate(userInput)) {
                    newBand.setName(userInput);
                    MyConsole.println(successMessage);
                    break;
                }
                else {
                    MyConsole.println(validateMessage);
                }
            } catch (IncorrectInputException e) {
                MyConsole.println(errorMessage);
            } catch (EmptyLineException e) {
                MyConsole.println(e.getMessage());
            }
        }
    }

    public void requestUserMusicBandDescription(
            MusicBand newBand,
            InputHandler inputHandler,
            String inputMessage,
            String successMessage,
            String errorMessage
    ) {
        while (true) {
            MyConsole.println(inputMessage);
            try {
                inputHandler.setInputFromConsole();
                String userInput = inputHandler.getInput();
                if (userInput.trim().equals("")) {
                    continue;
                }
                newBand.setDescription(userInput);
                MyConsole.println(successMessage);
                break;
            } catch (IncorrectInputException e) {
                MyConsole.println(errorMessage);
            } catch (EmptyLineException e) {
                MyConsole.println(e.getMessage());
            }
        }
    }

    /**
     * request object
     * @param newBand
     * @param inputHandler
     * @param inputMessage
     * @param validator
     * @param successMessage
     * @param errorMessage
     * @param validateMessage
     */
    public void requestUserNumberOfParticipants(
            MusicBand newBand,
            InputHandler inputHandler,
            String inputMessage,
            Validatable validator,
            String successMessage,
            String errorMessage,
            String validateMessage
    ) {
        while(true) {
            MyConsole.println(inputMessage);
            try {
                inputHandler.setInputFromConsole();
                int numberOfParticipants = inputHandler.getIntInput();

                if (validator.validate(numberOfParticipants)) {
                    newBand.setNumberOfParticipants(numberOfParticipants);
                    MyConsole.println(successMessage);
                    break;
                }
               else {
                    MyConsole.println(validateMessage);
                }
            } catch (IncorrectInputException | NumberFormatException e) {
                MyConsole.println(errorMessage);
            } catch (EmptyLineException e) {
                MyConsole.println(e.getMessage());
            }
        }
    }

    public void requestUserEstablishmentDate(
            MusicBand newBand,
            InputHandler inputHandler,
            String inputMessage,
            String successMessage,
            String errorMessage
    ) {
        while(true) {
            MyConsole.println(inputMessage);
            try {
                inputHandler.setInputFromConsole();
                ZonedDateTime establishmentDate = inputHandler.getDateInput();
                newBand.setEstablishmentDate(establishmentDate);
                MyConsole.println(successMessage);
                return;
            } catch (DateTimeParseException e) {
                MyConsole.println(errorMessage);
            } catch (IncorrectInputException e) {
                MyConsole.println(e.getMessage());
            } catch (EmptyLineException e) {
                MyConsole.println(e.getMessage());
            }
        }
    }

    public MusicGenre requestUserGenre(
            InputHandler inputHandler,
            String inputMessage,
            String successMessage,
            String error1Message,
            String error2Message
    ) {
        MusicGenre[] array = MusicGenre.values();
        MusicGenre resultingGenre;
        while(true){
            MyConsole.println("Список доступных жанров:");
            MusicGenre.showGenres();
            MyConsole.println(inputMessage);
            try {
                inputHandler.setInputFromConsole();
                int userIntInput = inputHandler.getIntInput();
                resultingGenre = array[userIntInput-1];
                MyConsole.println(successMessage);
                return resultingGenre;
            } catch (ArrayIndexOutOfBoundsException e) {
                MyConsole.println(error1Message);
            } catch (NumberFormatException e) {
                MyConsole.println(error2Message);
            } catch (IncorrectInputException e) {
                MyConsole.println(e.getMessage());
            } catch (EmptyLineException e) {
                MyConsole.println(e.getMessage());
            }
        }
    }
}
