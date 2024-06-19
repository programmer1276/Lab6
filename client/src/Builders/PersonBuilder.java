package Builders;

import Classes.Person;
import Console.MyConsole;
import Exceptions.EmptyLineException;
import Exceptions.IncorrectInputException;
import Managers.InputHandler;
import Validators.NameValidator;
import Validators.PassportIdValidator;
import Validators.Validatable;
import Validators.WeightValidator;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;


public class PersonBuilder implements Builder<Person> {

    /**
     * request my a field person for new MusicBand
     * @return
     * @throws IncorrectInputException
     */


    @Override
    public Person build() throws IncorrectInputException {
        InputHandler inputHandler = InputHandler.getInstance();
        NameValidator nameValidator = new NameValidator();
        WeightValidator weightValidator = new WeightValidator();
        PassportIdValidator passportIdValidator = new PassportIdValidator();
        MyConsole.println("Генерация посетителя(зрителя)...");
        MyConsole.println();
        Person person = new Person(null, null, 0, null, null);

        //имя
        requestName(person, inputHandler, nameValidator, "Введите имя посетителя:", "Имя было добавлено!", "Имя не может быть пустым!", "Некорректный ввод, попробуйте снова");

        //дата рождения
        MyConsole.println();
        requestBirthDate(person, inputHandler, "Введите дату рождения в формате 'yyyy-MM-dd':", "Дата рождения изменена успешно!", "Неверный формат даты. Введите дату в формате yyyy-MM-dd");

        //вес
        MyConsole.println();
        requestWeight(person, inputHandler, weightValidator, "Введите ваш вес(должен быть > 0):", "Вес успешно изменен!", "Вес не может быть отрицательным!", "Введено число неверного типа либо введено вообще не число! Пожалуйста, введите число еще раз(тип int)");

        //ID пасспорта
        MyConsole.println();
        requestPassportId(person, inputHandler, passportIdValidator, "Введите ID вашего пасспорта(4 =< ID.length =< 42):", "ID пасспорта успешно изменен", "Длина ID пасспорта должна быть >= 4 и <= 42", "Некорректный ввод, попробуйте снова!");

        //национальность
        MyConsole.println();
        requestCountry(person, inputHandler, "Введите вашу национальность(Большими латинскими буквами обязательно!):", "Национальность успешно изменена!");

        MyConsole.println("Идентификация завершена успешно!");
        MyConsole.println();
        return person;
    }

    /**
     * requests person name from user
     * @param person
     * @param inputHandler
     * @param validator
     * @param inputMessage
     * @param successMessage
     * @param validateMessage
     * @param errorMessage
     */
    public void requestName(Person person, InputHandler inputHandler, Validatable validator, String inputMessage, String successMessage, String validateMessage, String errorMessage) {
        while(true){
            MyConsole.println(inputMessage);
            try {
                inputHandler.setInputFromConsole();
                String userInput = inputHandler.getInput();
                if (userInput.trim().equals("")) {
                    continue;
                }
                if(validator.validate(userInput)){
                    person.setName(userInput);
                    MyConsole.println(successMessage);
                    break;
                }
                else{
                    MyConsole.println(validateMessage);
                }
            } catch (IncorrectInputException e) {
                MyConsole.println(errorMessage);
            } catch (EmptyLineException e) {
                MyConsole.println(e.getMessage());
            }
        }
    }

    /**
     * requests person birthdate from user
     * @param person
     * @param inputHandler
     * @param inputMessage
     * @param successMessage
     * @param errorMessage
     */
    public void requestBirthDate(
            Person person,
            InputHandler inputHandler,
            String inputMessage,
            String successMessage,
            String errorMessage
    ) {
        while (true) {
            MyConsole.println(inputMessage);
            try {
                inputHandler.setInputFromConsole();
                ZonedDateTime birthDate = inputHandler.getDateInput();
                if (birthDate != null) {
                    person.setBirthDate(birthDate);
                    MyConsole.println(successMessage);
                    break;
                }
            } catch (DateTimeParseException e) {
                MyConsole.println(errorMessage);
            } catch (IncorrectInputException e) {
                MyConsole.println(e.getMessage());
            } catch (EmptyLineException e) {
                MyConsole.println(e.getMessage());
            }
        }
    }

    /**
     * requests person weight from user
     * @param person
     * @param inputHandler
     * @param validator
     * @param inputMessage
     * @param successMessage
     * @param validateMessage
     * @param errorMessage
     */
    public void requestWeight(
            Person person,
            InputHandler inputHandler,
            Validatable validator,
            String inputMessage,
            String successMessage,
            String validateMessage,
            String errorMessage
    ) {
        while (true) {
            MyConsole.println(inputMessage);
            try {
                inputHandler.setInputFromConsole();
                Integer weight = inputHandler.getIntInput();
                if (validator.validate(weight)) {
                    person.setWeight(weight);
                    MyConsole.println(successMessage);
                    break;
                }
                else {
                    MyConsole.println(validateMessage);
                }
            } catch (NumberFormatException e) {
                MyConsole.println(errorMessage);
            } catch (EmptyLineException e) {
                MyConsole.println(e.getMessage());
            }
        }
    }

    /**
     * requests person passport Id from user
     * @param person
     * @param inputHandler
     * @param validator
     * @param inputMessage
     * @param successMessage
     * @param validateMessage
     * @param errorMessage
     */
    public void requestPassportId(
            Person person,
            InputHandler inputHandler,
            Validatable validator,
            String inputMessage,
            String successMessage,
            String validateMessage,
            String errorMessage
    ) {
        MyConsole.println();
        while (true) {
            MyConsole.println(inputMessage);
            try {
                inputHandler.setInputFromConsole();
                String userInput = inputHandler.getInput();
                if (userInput.trim().equals("")) {
                    continue;
                }
                if (validator.validate(userInput)) {
                    person.setPassportID(userInput);
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

    /**
     * requests person nationality from user
     * @param person
     * @param inputHandler
     * @param inputMessage
     * @param successMessage
     */
    public void requestCountry(
            Person person,
            InputHandler inputHandler,
            String inputMessage,
            String successMessage
    ) {
        while (true) {
            MyConsole.println(inputMessage);
            try {
                inputHandler.setCountryInputFromConsole();
                String userInput = inputHandler.getCountryInput();
                if (userInput != null) {
                    person.setNationality(userInput);
                    MyConsole.println(successMessage);
                    break;
                }
            } catch (IncorrectInputException e) {
                MyConsole.println(e.getMessage());
            } catch (EmptyLineException e) {
                MyConsole.println(e.getMessage());
            }
        }
    }
}
