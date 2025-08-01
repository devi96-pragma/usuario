package com.plazoleta.usuario.domain.usecase;

import com.plazoleta.usuario.domain.api.IAuthServicePort;
import com.plazoleta.usuario.domain.api.ITokenServicePort;
import com.plazoleta.usuario.domain.constantes.Constantes;
import com.plazoleta.usuario.domain.exception.CredencialesInvalidasException;
import com.plazoleta.usuario.domain.exception.UsuarioNoEncontradoException;
import com.plazoleta.usuario.domain.model.Rol;
import com.plazoleta.usuario.domain.model.Usuario;
import com.plazoleta.usuario.domain.spi.IPasswordEncoderPort;
import com.plazoleta.usuario.domain.spi.IUsuarioPersistencePort;

import java.util.List;

public class AuthUseCase implements IAuthServicePort {
    private final ITokenServicePort tokenServicePort;
    private final IUsuarioPersistencePort usuarioPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;

    public AuthUseCase(ITokenServicePort tokenServicePort, IUsuarioPersistencePort usuarioPersistencePort, IPasswordEncoderPort passwordEncoderPort) {
        this.tokenServicePort = tokenServicePort;
        this.usuarioPersistencePort = usuarioPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public String login(String username, String password) {
        Usuario usuario = usuarioPersistencePort.buscarUsuarioPorEmail(username)
                .orElseThrow(() -> new UsuarioNoEncontradoException(Constantes.MensajesError.USUARIO_NO_ENCONTRADO));

        if (!passwordEncoderPort.matches(password, usuario.getClave())) {
            throw new CredencialesInvalidasException(Constantes.MensajesError.CREDENCIALES_INVALIDAS);
        }
        Rol rol = usuario.getRol();
        return tokenServicePort.generateToken(usuario, List.of(rol.name()));
    }
}
