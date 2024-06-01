package fr.radewon.exceptions;

public class ExceedsBlockLimitException extends Exception{
    public ExceedsBlockLimitException(String msg) {
        super(msg);
    }

    public ExceedsBlockLimitException() {
        this("Number of blocks too high!");
    }
}
