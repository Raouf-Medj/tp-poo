package model.Exceptions;

public class NotFitInDayExeception extends Exception{
    @Override
    public String getMessage() {
        return "your task 'or' time slot does\n not seem to fit in this day";
    }
}
