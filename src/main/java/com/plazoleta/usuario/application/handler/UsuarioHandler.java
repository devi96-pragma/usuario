package com.plazoleta.usuario.application.handler;

import com.plazoleta.usuario.application.dto.PropietarioRequestDto;
import com.plazoleta.usuario.application.dto.PropietarioResponseDto;
import com.plazoleta.usuario.application.mapper.IPropietarioRequestMapper;
import com.plazoleta.usuario.domain.api.IUsuarioServicePort;
import com.plazoleta.usuario.domain.exception.UsuarioNoEsPropietarioException;
import com.plazoleta.usuario.domain.model.Rol;
import com.plazoleta.usuario.domain.model.Usuario;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioHandler implements IUsuarioHandler{

    private final IUsuarioServicePort usuarioServicePort;
    private final IPropietarioRequestMapper usuarioRequestMapper;

    @Override
    public String crearUsuarioPropietario(PropietarioRequestDto request) {
        Usuario usuario = usuarioRequestMapper.toDomain(request);
        return usuarioServicePort.crearPropietario(usuario);
    }

    @Override
    public PropietarioResponseDto buscarUsuarioPorId(Long idUsuario) {
        Usuario usuario = usuarioServicePort.buscarUsuarioPorId(idUsuario);
        return usuarioRequestMapper.toResponse(usuario);
    }
}
