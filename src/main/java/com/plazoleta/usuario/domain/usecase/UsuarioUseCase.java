package com.plazoleta.usuario.domain.usecase;

import com.plazoleta.usuario.domain.api.IPasswordEncoderPort;
import com.plazoleta.usuario.domain.api.IUsuarioServicePort;
import com.plazoleta.usuario.domain.model.Rol;
import com.plazoleta.usuario.domain.model.Usuario;
import com.plazoleta.usuario.domain.spi.IUsuarioPersistencePort;
import com.plazoleta.usuario.domain.validator.EdadValidator;

import java.util.List;

public class UsuarioUseCase implements IUsuarioServicePort {

    private final IUsuarioPersistencePort usuarioPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;

    public UsuarioUseCase(IUsuarioPersistencePort usuarioPersistencePort, IPasswordEncoderPort passwordEncoderPort) {
        this.usuarioPersistencePort = usuarioPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public void crearPropietario(Usuario usuario) {
        EdadValidator.validarMayorDeEdad(usuario.getFechaNacimiento());
        usuario.setRol(Rol.PROPIETARIO);
        usuario.setClave(passwordEncoderPort.encode(usuario.getClave()));
        usuarioPersistencePort.guardarPropietario(usuario);
    }
}
