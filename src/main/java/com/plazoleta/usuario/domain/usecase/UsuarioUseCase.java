package com.plazoleta.usuario.domain.usecase;

import com.plazoleta.usuario.domain.constantes.Constantes;
import com.plazoleta.usuario.domain.exception.UsuarioNoEsPropietarioException;
import com.plazoleta.usuario.domain.api.ITokenServicePort;
import com.plazoleta.usuario.domain.exception.UsuarioNoEncontradoException;
import com.plazoleta.usuario.domain.model.EmpleadoCreadoEvento;
import com.plazoleta.usuario.domain.spi.IEmpleadoEventPublisherPort;
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
    private final IEmpleadoEventPublisherPort empleadoEventPublisherPort;
    public UsuarioUseCase(IUsuarioPersistencePort usuarioPersistencePort, IPasswordEncoderPort passwordEncoderPort, IEmpleadoEventPublisherPort empleadoEventPublisherPort) {
        this.usuarioPersistencePort = usuarioPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.empleadoEventPublisherPort = empleadoEventPublisherPort;
    }

    @Override
    public Usuario buscarUsuarioPorId(Long idUsuario) {
        Usuario usuario = usuarioPersistencePort.buscarUsuarioPorId(idUsuario)
                .orElseThrow(() -> new UsuarioNoEncontradoException(Constantes.MensajesError.USUARIO_NO_ENCONTRADO));
        return usuario;
    }

    @Override
    public void crearUsuarioPropietario(Usuario usuario) {
        crearUsuarioConRol(usuario, Rol.PROPIETARIO);
    }
    @Override
    public void crearUsuarioEmpleado(Usuario usuario, Long idRestaurante) {
        Usuario usuarioGuardado = crearUsuarioConRol(usuario, Rol.EMPLEADO);
        EmpleadoCreadoEvento evento = new EmpleadoCreadoEvento(usuarioGuardado.getId(),idRestaurante);
        empleadoEventPublisherPort.publicarEmpleadoCreado(evento);
    }

    @Override
    public void crearUsuarioCliente(Usuario usuario) {
        crearUsuarioConRol(usuario, Rol.CLIENTE);
    }

    private Usuario crearUsuarioConRol(Usuario usuario, Rol rol) {
        EdadValidator.validarMayorDeEdad(usuario.getFechaNacimiento());
        usuario.setRol(rol);
        usuario.setClave(passwordEncoderPort.encode(usuario.getClave()));
        return usuarioPersistencePort.guardarUsuario(usuario); // este reemplaza a guardarEmpleado / guardarPropietario
    }
}
