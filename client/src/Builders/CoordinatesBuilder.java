package Builders;

import Classes.Coordinates;
import Console.MyConsole;
import Exceptions.EmptyLineException;
import Exceptions.IncorrectInputException;
import Managers.InputHandler;
import Validators.CoordXValidator;
import Validators.CoordYValidator;
import Validators.Validatable;


/**
 * Class to build field coordinates
 */
public class CoordinatesBuilder implements Builder<Coordinates> {

    @Override
    public Coordinates build() throws IncorrectInputException {
        InputHandler inputHandler = InputHandler.getInstance();
        MyConsole.println("Генерация координат...");
        MyConsole.println();
        Coordinates coordinates = new Coordinates(0, 0d);
        CoordXValidator coordXValidator = new CoordXValidator();
        CoordYValidator coordYValidator = new CoordYValidator();

        requestCoordX(coordinates, inputHandler, "Введите координату x(long, x > -97):", coordXValidator, "Координата 'x' была добавлена!", "Введено число неверного типа либо введено вообще не число! Пожалуйста, введите число еще раз(тип long)", "На координату 'x' есть ограничение(x > -97). Пожалуйста, введите координату снова");
        requestCoordY(coordinates, inputHandler, "Введите координату y(Double, y > -79):", coordYValidator, "Координата 'y' была добавлена!", "Введено число неверного типа либо введено вообще не число! Пожалуйста, введите число еще раз(тип long)", "На координату 'y' есть ограничение(y > -79 && y != null). Пожалуйста, введите координату снова");

        MyConsole.println("Генерация координат завершена!");
        return coordinates;
    }

    /**
     * Method to request an X coordinate from user
     * @param coordinates
     * @param inputHandler
     * @param inputMessage
     * @param validator
     * @param successMessage
     * @param errorMessage
     * @param validateMessage
     */
    public void requestCoordX(
            Coordinates coordinates,
            InputHandler inputHandler,
            String inputMessage,
            Validatable validator,
            String successMessage,
            String errorMessage,
            String validateMessage
    ) {
        while (true) {
            MyConsole.println(inputMessage);
            try {
                inputHandler.setInputFromConsole();
                long coordX = inputHandler.getLongInput();
                if (validator.validate(coordX)) {
                    coordinates.setX(coordX);
                    MyConsole.println(successMessage);
                    break;
                }
                else {
                    MyConsole.println(validateMessage);
                }
            } catch (NumberFormatException e) {
                MyConsole.println(errorMessage);
            } catch (IncorrectInputException e) {
                MyConsole.println(e.getMessage());
            } catch (EmptyLineException e) {
                MyConsole.println(e.getMessage());
            }
        }
    }

    /**
     * Method to request an Y coordinate from user
     * @param coordinates
     * @param inputHandler
     * @param inputMessage
     * @param validator
     * @param successMessage
     * @param errorMessage
     * @param validateMessage
     */
    public void requestCoordY(
            Coordinates coordinates,
            InputHandler inputHandler,
            String inputMessage,
            Validatable validator,
            String successMessage,
            String errorMessage,
            String validateMessage
    ) {
        while(true){
            MyConsole.println(inputMessage);
            try {
                inputHandler.setInputFromConsole();
                Double userInput = inputHandler.getDoubleInput();
                if (validator.validate(userInput)) {
                    coordinates.setY(userInput);
                    MyConsole.println(successMessage);
                    break;
                }
                else {
                    MyConsole.println(validateMessage);
                }
            } catch (NumberFormatException e) {
                MyConsole.println(errorMessage);
            } catch (IncorrectInputException e) {
                MyConsole.println(e.getMessage());
            } catch (EmptyLineException e) {
                MyConsole.println(e.getMessage());
            }
        }
    }
}
