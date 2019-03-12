package by.epam.calculator.comments;

public class InvalidInputException extends RuntimeException {
    private String message;

    public InvalidInputException(String s) {
        super();
        this.message = s;

    }

    @Override
    public String getMessage() {
        return message;
    }

}
