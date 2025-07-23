package com.plazoleta.usuario.infrastructure.out.jpa.adapter;

import com.plazoleta.usuario.domain.model.Usuario;
import com.plazoleta.usuario.domain.spi.IUsuarioPersistencePort;
import com.plazoleta.usuario.infrastructure.out.jpa.mapper.IUsuarioEntityMapper;
import com.plazoleta.usuario.infrastructure.out.jpa.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UsuarioJpaAdapter implements IUsuarioPersistencePort {

    private final IUsuarioRepository usuarioRepository;
    private final IUsuarioEntityMapper usuarioEntityMapper;

    @Override
    public void saveUsuario(Usuario usuario) {
        usuarioRepository.save(usuarioEntityMapper.toEntity(usuario));
    }

    @Override
    public Usuario getUsuario(Long id) {
        return null;
    }

    @Override
    public void deleteUsuario(Long id) {

    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return List.of();
    }

    @Override
    public Usuario updateUsuario(Long id, Usuario usuario) {
        return null;
    }
}
