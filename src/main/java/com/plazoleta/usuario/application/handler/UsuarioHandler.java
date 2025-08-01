package com.plazoleta.usuario.application.handler;

import com.plazoleta.usuario.application.dto.EmpleadoRequestDto;
import com.plazoleta.usuario.application.dto.UsuarioRequestDto;
import com.plazoleta.usuario.application.dto.UsuarioResponseDto;
import com.plazoleta.usuario.application.mapper.IEmpleadoRequestMapper;
import com.plazoleta.usuario.application.mapper.IUsuarioRequestMapper;
import com.plazoleta.usuario.domain.api.IUsuarioServicePort;
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
    private final IUsuarioRequestMapper usuarioRequestMapper;
    private final IEmpleadoRequestMapper empleadoRequestMapper;
    @Override
    public void crearPropietario(UsuarioRequestDto request) {
        Usuario usuario = usuarioRequestMapper.toDomain(request);
        usuarioServicePort.crearUsuarioPropietario(usuario);
    }
    @Override
    public void crearEmpleado(EmpleadoRequestDto request) {
        Usuario usuario = empleadoRequestMapper.toDomain(request);
        usuarioServicePort.crearUsuarioEmpleado(usuario, request.getIdRestaurante());
    }
    @Override
    public void crearCliente(UsuarioRequestDto request) {
        Usuario usuario = usuarioRequestMapper.toDomain(request);
        usuarioServicePort.crearUsuarioCliente(usuario);
    }


    @Override
    public UsuarioResponseDto buscarUsuarioPorId(Long idUsuario) {
        Usuario usuario = usuarioServicePort.buscarUsuarioPorId(idUsuario);
        return usuarioRequestMapper.toResponse(usuario);
    }
}
