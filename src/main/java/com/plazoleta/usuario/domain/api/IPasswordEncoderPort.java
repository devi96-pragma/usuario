package com.plazoleta.usuario.domain.api;

public interface IPasswordEncoderPort {
    String encode(String password);
    boolean matches(String rawPassword, String encodedPassword);
}
