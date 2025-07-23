package com.plazoleta.usuario.domain.api;

import com.plazoleta.usuario.domain.model.Usuario;

import java.util.List;

public interface IUsuarioServicePort {
    void saveUsuario(Usuario usuario);
    Usuario getUsuario(Long id);
    void deleteUsuario(Long id);
    List<Usuario> getAllUsuarios();
    Usuario updateUsuario(Long id, Usuario usuario);
}
