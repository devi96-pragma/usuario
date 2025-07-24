package com.plazoleta.usuario.infrastructure.exception;

public class UsuarioNoMayorEdadException extends RuntimeException {
    public UsuarioNoMayorEdadException(String message) {
        super(message);
    }
}
