package Validators;

public class WeightValidator implements Validatable<Integer> {
    @Override
    public boolean validate(Integer value) {
        return value >= 0;
    }
}
