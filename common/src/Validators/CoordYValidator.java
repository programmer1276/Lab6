package Validators;

public class CoordYValidator implements Validatable<Double> {
    @Override
    public boolean validate(Double value) {
        return (value > -79) && (value != null);
    }
}
