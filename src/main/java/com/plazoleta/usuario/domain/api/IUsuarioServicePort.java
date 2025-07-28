package com.plazoleta.usuario.domain.api;

import com.plazoleta.usuario.domain.model.Usuario;

import java.util.Optional;


public interface IUsuarioServicePort {
    String crearPropietario(Usuario usuario);
    Usuario buscarUsuarioPorId(Long idUsuario);

}
