package com.plazoleta.usuario.application.handler;

import com.plazoleta.usuario.application.dto.PropietarioRequestDto;
import com.plazoleta.usuario.application.mapper.IPropietarioRequestMapper;
import com.plazoleta.usuario.domain.api.IUsuarioServicePort;
import com.plazoleta.usuario.domain.model.Usuario;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminHandler implements IAdminHandler {
    private final IUsuarioServicePort usuarioServicePort;
    private final IPropietarioRequestMapper usuarioRequestMapper;
    @Override
    public void crearPropietario(PropietarioRequestDto request) {
        Usuario usuario = usuarioRequestMapper.toDomain(request);
        log.info("Creando propietario: {}", usuario.getFechaNacimiento());
        usuarioServicePort.crearPropietario(usuario);
    }

}
