package com.plazoleta.usuario.domain.spi;

import com.plazoleta.usuario.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioPersistencePort {
    Usuario guardarUsuario(Usuario usuario);
    Optional<Usuario> buscarUsuarioPorId(Long idUsuario);
    Optional<Usuario> buscarUsuarioPorEmail(String email);
}
