package com.wanhuchina.common.util.number;

/**
 * @author Sorin Cazacu
 */
public class InvalidNumberException extends RuntimeException {

    public InvalidNumberException() {
        super();
    }

    public InvalidNumberException(String message) {
        super(message);
    }

    public InvalidNumberException(Throwable th) {
        super(th);
    }
}
