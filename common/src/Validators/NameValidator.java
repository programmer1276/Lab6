package Validators;

public class NameValidator implements Validatable<String> {
    @Override
    public boolean validate(String value) {
        return !value.isEmpty() && !value.isBlank() && value != null;
    }
}