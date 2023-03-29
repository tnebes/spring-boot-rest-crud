package hr.tnebes.crud.controllers.exceptionhandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ControllerAdvice
@RestController
@Slf4j
public class ProductControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(final MethodArgumentNotValidException exception) {
        final BindingResult result = exception.getBindingResult();
        final List<String> errorMessages = result.getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .toList();
        log.error("Validation error: {}", errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
    }

}
