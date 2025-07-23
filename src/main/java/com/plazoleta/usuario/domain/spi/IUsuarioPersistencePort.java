package com.plazoleta.usuario.domain.spi;

import com.plazoleta.usuario.domain.model.Usuario;

import java.util.List;

public interface IUsuarioPersistencePort {
    void saveUsuario(Usuario usuario);
    Usuario getUsuario(Long id);
    void deleteUsuario(Long id);
    List<Usuario> getAllUsuarios();
    Usuario updateUsuario(Long id, Usuario usuario);
}
