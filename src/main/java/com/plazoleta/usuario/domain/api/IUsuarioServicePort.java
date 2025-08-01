package com.plazoleta.usuario.domain.api;

import com.plazoleta.usuario.domain.model.Rol;
import com.plazoleta.usuario.domain.model.Usuario;

import java.util.Optional;


public interface IUsuarioServicePort {
    void crearUsuarioPropietario(Usuario usuario);
    void crearUsuarioEmpleado(Usuario usuario, Long idRestaurante);
    void crearUsuarioCliente(Usuario usuario);
    //String crearEmpleado(Usuario usuario);
    Usuario buscarUsuarioPorId(Long idUsuario);

}
