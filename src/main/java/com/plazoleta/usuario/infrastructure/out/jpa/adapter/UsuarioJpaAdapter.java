package com.plazoleta.usuario.infrastructure.out.jpa.adapter;

import com.plazoleta.usuario.domain.model.Usuario;
import com.plazoleta.usuario.domain.spi.IUsuarioPersistencePort;
import com.plazoleta.usuario.infrastructure.out.jpa.mapper.IUsuarioEntityMapper;
import com.plazoleta.usuario.infrastructure.out.jpa.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
public class UsuarioJpaAdapter implements IUsuarioPersistencePort {

    private final IUsuarioRepository usuarioRepository;
    private final IUsuarioEntityMapper usuarioEntityMapper;

    @Override
    public void guardarPropietario(Usuario usuario) {
        usuarioRepository.save(usuarioEntityMapper.toEntity(usuario));
    }
}
