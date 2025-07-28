package com.plazoleta.usuario.application.handler;

import com.plazoleta.usuario.application.dto.PropietarioRequestDto;
import com.plazoleta.usuario.application.dto.PropietarioResponseDto;

public interface IUsuarioHandler {
    String crearUsuarioPropietario(PropietarioRequestDto request);
    PropietarioResponseDto buscarUsuarioPorId(Long idUsuario);
}
