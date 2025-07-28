package com.plazoleta.usuario.domain.usecase;

import com.plazoleta.usuario.domain.exception.UsuarioNoEsPropietarioException;
import com.plazoleta.usuario.domain.api.ITokenServicePort;
import com.plazoleta.usuario.domain.exception.UsuarioNoEncontradoException;
import com.plazoleta.usuario.domain.spi.IPasswordEncoderPort;
import com.plazoleta.usuario.domain.api.IUsuarioServicePort;
import com.plazoleta.usuario.domain.model.Rol;
import com.plazoleta.usuario.domain.model.Usuario;
import com.plazoleta.usuario.domain.spi.IUsuarioPersistencePort;
import com.plazoleta.usuario.domain.validator.EdadValidator;

import java.util.List;

public class UsuarioUseCase implements IUsuarioServicePort {

    private final IUsuarioPersistencePort usuarioPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final ITokenServicePort tokenServicePort;

    public UsuarioUseCase(IUsuarioPersistencePort usuarioPersistencePort, IPasswordEncoderPort passwordEncoderPort, ITokenServicePort tokenServicePort) {
        this.usuarioPersistencePort = usuarioPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenServicePort = tokenServicePort;
    }

    @Override
    public String crearPropietario(Usuario usuario) {
        EdadValidator.validarMayorDeEdad(usuario.getFechaNacimiento());
        usuario.setRol(Rol.PROPIETARIO);
        usuario.setClave(passwordEncoderPort.encode(usuario.getClave()));
        usuarioPersistencePort.guardarPropietario(usuario);
        return tokenServicePort.generateToken(usuario, List.of(usuario.getRol().name()));
    }

    @Override
    public Usuario buscarUsuarioPorId(Long idUsuario) {
        Usuario usuario = usuarioPersistencePort.buscarUsuarioPorId(idUsuario)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + idUsuario));
        /*if(!usuario.getRol().equals(Rol.PROPIETARIO)){
            throw new UsuarioNoEsPropietarioException("El usuario con ID " + idUsuario + " no es un propietario.");
        }*/
        return usuario;
    }

}
