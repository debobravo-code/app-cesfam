package cl.duoc.CesfamInterno.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(
            MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errores);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> manejarIllegalArgument(
            IllegalArgumentException ex) {

        Map<String, String> error = new HashMap<>();

        if ("Funcionario no encontrado".equals(ex.getMessage())) {
            error.put("error", ex.getMessage());

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(error);
        }

        error.put("error", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}