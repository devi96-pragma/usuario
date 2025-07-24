package com.plazoleta.usuario.infrastructure.exceptionhandler;

import com.plazoleta.usuario.infrastructure.exception.UsuarioNoMayorEdadException;
import com.plazoleta.usuario.infrastructure.exception.UsuarioSinFechaNacimientoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

@ControllerAdvice
public class ControllerAdvisor {
    private static final String MESSAGE = "message";
    @ExceptionHandler(UsuarioSinFechaNacimientoException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioSinFechaNacimientoException(UsuarioSinFechaNacimientoException e) {
        Map<String, String> response = new HashMap<>();
        response.put(MESSAGE, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, e.getMessage()));
    }
    @ExceptionHandler(UsuarioNoMayorEdadException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioNoMayorEdadException(UsuarioNoMayorEdadException e) {
        Map<String, String> response = new HashMap<>();
        response.put(MESSAGE, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, e.getMessage()));
    }
}
