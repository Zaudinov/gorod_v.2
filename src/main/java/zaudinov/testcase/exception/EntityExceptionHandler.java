package zaudinov.testcase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class EntityExceptionHandler {

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<Object> handleUserNotFound(
            UserNotExistsException ex,
            WebRequest request){
        return new ResponseEntity<Object>(new ApiException(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServNotExistsException.class)
    public ResponseEntity<Object> handleServiceNotFound(
            ServNotExistsException ex,
            WebRequest request){
        return new ResponseEntity<Object>(new ApiException(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserCreationException(
            UserAlreadyExistsException ex,WebRequest request){
        return new ResponseEntity<Object>(new ApiException(
                ex.getMessage(),
                HttpStatus.CONFLICT,
                LocalDateTime.now()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CantDeleteServiceException.class)
    public ResponseEntity<Object> handleServHasChildrenException(
            CantDeleteServiceException ex, WebRequest request){
            return new ResponseEntity<Object>(new ApiException(
                    ex.getMessage(),
                    HttpStatus.CONFLICT,
                    LocalDateTime.now()), HttpStatus.CONFLICT);
    }
}
