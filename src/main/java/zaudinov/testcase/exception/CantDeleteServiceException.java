package zaudinov.testcase.exception;

public class CantDeleteServiceException extends RuntimeException{
    public CantDeleteServiceException(String message) {
        super(message);
    }
}
