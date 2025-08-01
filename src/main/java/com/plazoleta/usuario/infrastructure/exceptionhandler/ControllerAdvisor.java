package com.plazoleta.usuario.infrastructure.exceptionhandler;

import com.plazoleta.usuario.domain.exception.UsuarioNoEsPropietarioException;
import com.plazoleta.usuario.domain.exception.UsuarioNoEncontradoException;
import com.plazoleta.usuario.domain.exception.UsuarioNoMayorEdadException;
import com.plazoleta.usuario.domain.exception.UsuarioSinFechaNacimientoException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

@ControllerAdvice
public class ControllerAdvisor {
    private static final String MESSAGE = "message";

    private ResponseEntity<Map<String, String>> buildResponse(String mensaje, HttpStatus status) {
        return new ResponseEntity<>(
                Collections.singletonMap(MESSAGE, mensaje),
                status
        );
    }

    @ExceptionHandler(UsuarioSinFechaNacimientoException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioSinFechaNacimientoException(UsuarioSinFechaNacimientoException e) {
        return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UsuarioNoMayorEdadException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioNoMayorEdadException(UsuarioNoMayorEdadException e) {
        return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioNoEncontradoException(UsuarioNoEncontradoException e) {
        return buildResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UsuarioNoEsPropietarioException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioNoEsPropietarioException(UsuarioNoEsPropietarioException e) {
        return buildResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Throwable cause = ex.getCause();

        while (cause != null && !(cause instanceof SQLIntegrityConstraintViolationException)) {
            cause = cause.getCause();
        }

        if (cause instanceof SQLIntegrityConstraintViolationException constraintEx) {
            String message = constraintEx.getMessage();
            // Puedes agregar más validaciones aquí según el nombre del campo único
            if (message.contains("usuario.documento_de_identidad")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("message", "El documento de identidad ya está registrado."));
            } else if (message.contains("usuario.correo")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("message", "El correo ya está registrado."));
            }
        }

        // Para cualquier otra violación de integridad no controlada
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error de integridad de datos."));
    }

}
