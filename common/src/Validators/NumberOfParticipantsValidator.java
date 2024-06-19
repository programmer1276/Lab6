package Validators;

public class NumberOfParticipantsValidator implements Validatable<Integer> {
    @Override
    public boolean validate(Integer value) {
        return value > 0;
    }
}
