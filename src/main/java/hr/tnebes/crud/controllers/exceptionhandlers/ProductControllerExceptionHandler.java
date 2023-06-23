package hr.tnebes.crud.controllers.exceptionhandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
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
        return ResponseEntity.badRequest().body(errorMessages);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(final HttpMessageNotReadableException exception) throws IOException {
        log.error("Malformed or invalid data passed: {}", exception.getMessage(), exception);
        return ResponseEntity.badRequest().body("Malformed or invalid data passed.");
    }

}
