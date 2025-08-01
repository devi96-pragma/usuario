package com.plazoleta.usuario.infrastructure.out.jpa.adapter;

import com.plazoleta.usuario.domain.exception.UsuarioNoMayorEdadException;
import com.plazoleta.usuario.domain.model.Usuario;
import com.plazoleta.usuario.domain.spi.IUsuarioPersistencePort;
import com.plazoleta.usuario.infrastructure.out.jpa.mapper.IUsuarioEntityMapper;
import com.plazoleta.usuario.infrastructure.out.jpa.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@RequiredArgsConstructor
public class UsuarioJpaAdapter implements IUsuarioPersistencePort {

    private final IUsuarioRepository usuarioRepository;
    private final IUsuarioEntityMapper usuarioEntityMapper;

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return
                usuarioEntityMapper.toDomain(
                        usuarioRepository.save(usuarioEntityMapper.toEntity(usuario))
                );
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .map(usuarioEntityMapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByCorreo(email)
                .map(usuarioEntityMapper::toDomain);
    }
}
