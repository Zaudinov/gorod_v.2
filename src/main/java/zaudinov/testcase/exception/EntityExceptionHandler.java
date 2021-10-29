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
    public ResponseEntity<Object> handleSubscriberNotFound(
            UserNotExistsException ex,
            WebRequest request){
        return new ResponseEntity<Object>(new ApiException(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }
}
