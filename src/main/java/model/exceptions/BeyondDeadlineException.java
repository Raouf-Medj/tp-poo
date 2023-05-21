package model.exceptions;

public class BeyondDeadlineException extends Exception {
    @Override
    public String getMessage() {
        return "The Task you are trying to insert\n is beyond it's deadline";
    }
}
