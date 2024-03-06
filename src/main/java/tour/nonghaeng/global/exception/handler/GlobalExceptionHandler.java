package tour.nonghaeng.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tour.nonghaeng.global.exception.NongHaengException;
import tour.nonghaeng.global.exception.dto.ErrorReason;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NongHaengException.class)
    public ResponseEntity<ErrorReason> handleNongHaengException(NongHaengException e) {

        ErrorReason errorReason = e.getErrorReason();

        return new ResponseEntity<>(errorReason, HttpStatus.resolve(errorReason.getStatus()));
    }
}
