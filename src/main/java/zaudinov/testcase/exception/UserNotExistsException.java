package zaudinov.testcase.exception;

public class UserNotExistsException extends RuntimeException{
    public UserNotExistsException(String message) {
        super(message);
    }
}
