package Validators;

public class PassportIdValidator implements Validatable<String> {
    @Override
    public boolean validate(String value) {
        return (value.length() <= 42) && (value.length() >= 4);
    }
}
