package com.plazoleta.usuario.domain.usecase;

import com.plazoleta.usuario.domain.api.IUsuarioServicePort;
import com.plazoleta.usuario.domain.model.Usuario;
import com.plazoleta.usuario.domain.spi.IUsuarioPersistencePort;

import java.util.List;

public class UsuarioUseCase implements IUsuarioServicePort {

    private final IUsuarioPersistencePort usuarioPersistencePort;

    public UsuarioUseCase(IUsuarioPersistencePort usuarioPersistencePort) {
        this.usuarioPersistencePort = usuarioPersistencePort;
    }

    @Override
    public void saveUsuario(Usuario usuario) {
        usuarioPersistencePort.saveUsuario(usuario);
    }

    @Override
    public Usuario getUsuario(Long id) {
        return usuarioPersistencePort.getUsuario(id);
    }

    @Override
    public void deleteUsuario(Long id) {
        usuarioPersistencePort.deleteUsuario(id);
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioPersistencePort.getAllUsuarios();
    }

    @Override
    public Usuario updateUsuario(Long id, Usuario usuario) {
        return usuarioPersistencePort.updateUsuario(id, usuario);
    }
}
