package com.plazoleta.usuario.domain.api;

import com.plazoleta.usuario.domain.model.Usuario;

import java.util.List;

public interface ITokenServicePort {
    String generateToken(Usuario usuario, List<String> roles);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
}
