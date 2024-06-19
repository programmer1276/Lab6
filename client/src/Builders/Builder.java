package Builders;

import Exceptions.IncorrectInputException;

public interface Builder<T> {
    T build() throws IncorrectInputException;
}
