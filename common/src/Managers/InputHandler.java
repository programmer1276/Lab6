package Managers;

import Classes.Country;
import Classes.ListOfCountries;
import Console.MyConsole;
import Exceptions.EmptyLineException;
import Exceptions.IncorrectInputException;
import Validators.CountryValidator;
import Validators.CtrlDValidator;
import Validators.ValidateInput;
import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputHandler {
    private Scanner scanner = new Scanner(System.in);

    private static InputHandler inputHandlerSingleton;

    public static InputHandler getInstance() {
        if (inputHandlerSingleton == null){
            inputHandlerSingleton = new InputHandler();
        }
        return inputHandlerSingleton;
    }

    /**
     * Indicates current mode (user or non-user)
     */
    private static boolean flagOfUserMode = true;

    private String input;
    private String nationality = null;
    public void setflagOfUserMode(boolean flagOfUserMode) {
        InputHandler.flagOfUserMode = flagOfUserMode;
    }

    public boolean getflagOfUserMode() {
        return flagOfUserMode;
    }

    public void setInputFromConsole() throws EmptyLineException {
        printPrompt();
        switchToUserModeIfNeeded();

        String client_input = readInput();
        if (!(client_input == null)) client_input = client_input.trim();
        this.input = client_input;
        ctrlDChecker();
        if (this.input != null) {
            ValidateInput validateInput = new ValidateInput();
            if (validateInput.validate(input)) {
                printInputIfNeeded(input);
            }
        }
    }
    private void ctrlDChecker() {
        CtrlDValidator ctrlDValidator = new CtrlDValidator();
        if (ctrlDValidator.validate(input)) {
            MyConsole.println("Вы нажали ctrl + D. Завершение программы...");
            this.input = "exit";
        }
    }

    public String getInput() {
        return input;
    }

    private void printPrompt() {
        MyConsole.print("> ");
    }

    private void printInputIfNeeded(String input) {
        if (!isFlagOfUserMode()) {
            MyConsole.println(input);
        }
    }

    private boolean isFlagOfUserMode() {
        return flagOfUserMode;
    }

    private boolean userMode() {
        return this.scanner.hasNextLine();
    }

    private void switchToUserModeIfNeeded() {
        if (!userMode()) {
            switchToUserMode();
        }
    }

    private void switchToUserMode() {
        setflagOfUserMode(true);
        this.scanner = new Scanner(System.in);
    }

    private String readInput() {
        if (this.scanner.hasNextLine()) return this.scanner.nextLine();
        return null;
    }

    public void setInputFromFile(File file) throws IOException {
        setflagOfUserMode(false);
        this.scanner = new Scanner(file);
    }

    /**
     * enter from console
     * @return
     * @throws EmptyLineException
     * @throws NumberFormatException
     */

    public Integer getIntInput() throws NumberFormatException {
        return Integer.parseInt(this.input);
    }

    public long getLongInput() throws NumberFormatException {
        return Long.parseLong(this.input);
    }

    public Double getDoubleInput() throws NumberFormatException {
        input = input.replace(',', '.');
        return Double.parseDouble(this.input);
    }

    public ZonedDateTime getDateInput() throws DateTimeParseException {
        return LocalDate.parse(this.input, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay(ZoneId.systemDefault());
    }

    public void setCountryInputFromConsole() throws IncorrectInputException {
        MyConsole.println("Список доступных стран:");
        ListOfCountries.execute();
        inputHandlerSingleton.setInputFromConsole();
        String userInput = inputHandlerSingleton.getInput();
        for (var country : Country.values()) {
            if ((country.toString()).equals(userInput)) {
                this.nationality = country.toString();
                break;
            }
        }
        if (ListOfCountries.getCountries().containsKey(userInput)) this.nationality = ListOfCountries.getCountries().get(userInput);
        if (this.nationality == null) {
            MyConsole.println("Ввод не является ни числом, ни строчкой нужного типа!");
            return;
        }
        CountryValidator countryValidator = new CountryValidator();
        countryValidator.validate(this.nationality);
    }

    public String getCountryInput() {
        return this.nationality;
    }
}