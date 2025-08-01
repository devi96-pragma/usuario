package com.plazoleta.usuario.application.handler;

import com.plazoleta.usuario.application.dto.EmpleadoRequestDto;
import com.plazoleta.usuario.application.dto.UsuarioRequestDto;
import com.plazoleta.usuario.application.dto.UsuarioResponseDto;
import com.plazoleta.usuario.domain.model.Rol;

public interface IUsuarioHandler {
    void crearPropietario(UsuarioRequestDto request);
    void crearEmpleado(EmpleadoRequestDto request);
    void crearCliente(UsuarioRequestDto request);
    UsuarioResponseDto buscarUsuarioPorId(Long idUsuario);
}
