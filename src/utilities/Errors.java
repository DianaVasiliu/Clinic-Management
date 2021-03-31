package utilities;

public enum Errors {
    INVALID_YEAR("Invalid year"),
    INVALID_MONTH("Invalid month"),
    INVALID_DAY("Invalid day"),
    INVALID_PATIENT("Invalid patient"),
    WRONG_PERCENTAGE("Wrong percentage"),
    INVALID_DURATION("Invalid duration"),
    WRONG_AMOUNT("Wrong amount");

    public final String message;

    Errors(String message) {
        this.message = message;
    }
}
