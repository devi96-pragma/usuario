package com.plazoleta.usuario.domain.spi;

public interface IPasswordEncoderPort {
    String encode(String password);
    boolean matches(String rawPassword, String encodedPassword);
}
