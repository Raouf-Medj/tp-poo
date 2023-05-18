package model.Exceptions;

public class NotFitInZoneException extends Exception{
    @Override
    public String getMessage() {
        return "your task does not seem to \nfit inside its time slot";
    }
}
