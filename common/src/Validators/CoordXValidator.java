package Validators;

public class CoordXValidator implements Validatable<Long> {
    @Override
    public boolean validate(Long value) {
        return value > -97;
    }
}
