package com.plazoleta.usuario.infrastructure.exception;

public class UsuarioSinFechaNacimientoException extends RuntimeException {
    public UsuarioSinFechaNacimientoException(String s) {
        super(s);
    }
}