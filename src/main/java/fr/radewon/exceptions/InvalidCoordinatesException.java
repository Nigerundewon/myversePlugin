package fr.radewon.exceptions;

public class InvalidCoordinatesException extends Exception{
    public InvalidCoordinatesException(String msg) {
        super(msg);
    }

    public InvalidCoordinatesException() {
        this("Invalid coordinates");
    }
}
