package fr.radewon.exceptions;

public class NoBlockSelectedException extends Exception{
    public NoBlockSelectedException(String msg) {
        super(msg);
    }

    public NoBlockSelectedException() {
        this("No block selected");
    }
}
