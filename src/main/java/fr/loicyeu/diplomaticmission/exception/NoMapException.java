package fr.loicyeu.diplomaticmission.exception;

public class NoMapException extends GameMapException{
    public NoMapException() {
    }

    public NoMapException(String message) {
        super(message);
    }
}
