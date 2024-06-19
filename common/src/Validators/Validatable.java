package Validators;

public interface Validatable<T> {
    boolean validate(T value);
}
