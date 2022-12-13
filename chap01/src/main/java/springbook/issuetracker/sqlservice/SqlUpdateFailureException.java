package springbook.issuetracker.sqlservice;

public class SqlUpdateFailureException extends Exception {
    public SqlUpdateFailureException(String message) {
        super(message);
    }
}
