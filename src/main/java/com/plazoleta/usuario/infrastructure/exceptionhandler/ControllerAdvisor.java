package com.plazoleta.usuario.infrastructure.exceptionhandler;

import com.plazoleta.usuario.domain.exception.UsuarioNoEsPropietarioException;
import com.plazoleta.usuario.domain.exception.UsuarioNoEncontradoException;
import com.plazoleta.usuario.domain.exception.UsuarioNoMayorEdadException;
import com.plazoleta.usuario.domain.exception.UsuarioSinFechaNacimientoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

}
