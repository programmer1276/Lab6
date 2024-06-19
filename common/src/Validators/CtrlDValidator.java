package Validators;

public class CtrlDValidator implements Validatable<String> {
    @Override
    public boolean validate(String value) {
        return value == null;
    }
}
