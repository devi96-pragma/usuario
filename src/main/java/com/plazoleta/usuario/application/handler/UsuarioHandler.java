package com.plazoleta.usuario.application.handler;

import com.plazoleta.usuario.application.dto.UsuarioRequest;
import com.plazoleta.usuario.application.mapper.IUsuarioRequestMapper;
import com.plazoleta.usuario.domain.api.IUsuarioServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioHandler implements IUsuarioHandler{

    private final IUsuarioRequestMapper usuarioRequestMapper;
    private final IUsuarioServicePort usuarioServicePort;

    @Override
    public void crearUsuario(UsuarioRequest usuario) {
        usuarioServicePort.saveUsuario(
                usuarioRequestMapper.toDomain(usuario)
        );
    }
}
