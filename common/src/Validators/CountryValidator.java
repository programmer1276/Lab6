package Validators;

import Exceptions.IncorrectInputException;

public class CountryValidator implements Validatable<String> {
    @Override
    public boolean validate(String value) {
        if (value == null) {
            throw new IncorrectInputException("Попробуйте пожалуйста ввести страну еще раз(Большими латинскими буквами обязательно!). " +
                    "Возможно данная страна не поддерживается нашей программой.");
        }
        return true;
    }
}
