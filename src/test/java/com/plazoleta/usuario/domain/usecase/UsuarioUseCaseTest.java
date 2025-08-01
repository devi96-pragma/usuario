package com.plazoleta.usuario.domain.usecase;

import com.plazoleta.usuario.domain.exception.UsuarioNoEncontradoException;
import com.plazoleta.usuario.domain.exception.UsuarioNoMayorEdadException;
import com.plazoleta.usuario.domain.exception.UsuarioSinFechaNacimientoException;
import com.plazoleta.usuario.domain.model.EmpleadoCreadoEvento;
import com.plazoleta.usuario.domain.spi.IEmpleadoEventPublisherPort;
import com.plazoleta.usuario.domain.spi.IPasswordEncoderPort;
import com.plazoleta.usuario.domain.model.Rol;
import com.plazoleta.usuario.domain.model.Usuario;
import com.plazoleta.usuario.domain.spi.IUsuarioPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class UsuarioUseCaseTest {

    @Mock
    private IUsuarioPersistencePort usuarioPersistencePort;
    @Mock
    private IPasswordEncoderPort passwordEncoderPort;
    @InjectMocks
    private UsuarioUseCase usuarioUseCase;
    @Mock
    private IEmpleadoEventPublisherPort empleadoEventPublisherPort;
    //Happy path: Guardar propietario con clave hasheada y rol cambiado
    @Test
    void testGuardarPropietario_hashearClaveYCambiarRol() {
        // Arrange
        Usuario usuario = new Usuario(
                1L,
                null, // Rol se asignará en el use case
                "clave",
                "harold@gmail.com",
                LocalDate.of(1996,8, 9),
                "+51960259627",
                123456787,
                "Juan",
                "Pérez");
        when(passwordEncoderPort.encode("clave")).thenReturn("hashedClave");

        // Act
        usuarioUseCase.crearUsuarioPropietario(usuario);

        // Assert
        assertThat(usuario.getClave()).isEqualTo("hashedClave");
        assertThat(usuario.getRol()).isEqualTo(Rol.PROPIETARIO);
        verify(usuarioPersistencePort, times(1)).guardarUsuario(usuario);
    }
    //Negative path: Guardar propietario con fecha de nacimiento inválida (menor de edad)
    @Test
    void testGuardarPropietario_fechaNacimientoMenorDe18() {
        // Arrange
        Usuario usuario = new Usuario(
                1L,
                null, // Rol se asignará en el use case
                "clave",
                "harold@gmail.com",
                LocalDate.of(2020,8, 9),
                "+51960259627",
                123456787,
                "Juan",
                "Pérez");

        // Act + Assert
        assertThrows(UsuarioNoMayorEdadException.class, () -> {
            usuarioUseCase.crearUsuarioPropietario(usuario);
        });
        // Verificar que no se guarde el usuario porque la fecha de nacimiento es inválida
        verify(usuarioPersistencePort, never()).guardarUsuario(any());
    }
    //Negative path: Guardar propietario con fecha de nacimiento inválida (menor de edad)
    @Test
    void testGuardarPropietario_fechaNacimientoNull() {
        // Arrange
        Usuario usuario = new Usuario(
                1L,
                null, // Rol se asignará en el use case
                "clave",
                "harold@gmail.com",
                null,
                "+51960259627",
                123456787,
                "Juan",
                "Pérez");

        // Act + Assert
        assertThrows(UsuarioSinFechaNacimientoException.class, () -> {
            usuarioUseCase.crearUsuarioPropietario(usuario);
        });
        // Verificar que no se guarde el usuario porque la fecha de nacimiento es inválida
        verify(usuarioPersistencePort, never()).guardarUsuario(any());
    }
    //Happy path: Buscar usuario por ID
    @Test
    void testBuscarUsuarioPorId_existe() {
        // Arrange
        Long idUsuario = 1L;
        // Simular el usuario que se espera encontrar
        Usuario usuario = new Usuario(
                1L,
                Rol.ADMINISTRADOR, // Rol se asignará en el use case
                "hashedclave",
                "harold@gmail.com",
                LocalDate.of(1996, 8, 9),
                "+51960259627",
                123456787,
                "Juan",
                "Pérez");
        when(usuarioPersistencePort.buscarUsuarioPorId(idUsuario)).thenReturn(
                Optional.of(usuario));
        // Act
        Usuario resultado = usuarioUseCase.buscarUsuarioPorId(idUsuario);
        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(idUsuario);
        assertThat(resultado.getRol()).isEqualTo(Rol.ADMINISTRADOR);
        assertThat(resultado.getClave()).isEqualTo("hashedclave");
    }
    //Negative path: Buscar usuario por ID no existe
    @Test
    void testBuscarUsuarioPorId_noExiste() {
        // Arrange
        Long idUsuario = 999L; // ID que no existe
        when(usuarioPersistencePort.buscarUsuarioPorId(idUsuario)).thenReturn(Optional.empty());
        // Act + Assert
        assertThrows(UsuarioNoEncontradoException.class, () -> {
            usuarioUseCase.buscarUsuarioPorId(idUsuario);
        });
        // Assert
        verify(usuarioPersistencePort, times(1)).buscarUsuarioPorId(idUsuario);
    }
    //Happy path: Guardar empleado con clave hasheada y rol cambiado
    @Test
    void testGuardarEmpleado_hashearClaveYCambiarRol() {
        //Arrange
        Usuario usuario = new Usuario();
        usuario.setClave("clave");
        usuario.setFechaNacimiento(LocalDate.of(1996, 8, 9));
        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1L);
        usuarioGuardado.setClave("hashedClave");
        usuarioGuardado.setFechaNacimiento(LocalDate.of(1996, 8, 9));
        when(passwordEncoderPort.encode("clave")).thenReturn("hashedClave");
        when(usuarioPersistencePort.guardarUsuario(usuario)).thenReturn(usuarioGuardado);
        doNothing().when(empleadoEventPublisherPort).publicarEmpleadoCreado(any(EmpleadoCreadoEvento.class));
        //Act
        usuarioUseCase.crearUsuarioEmpleado(usuario, 1L);
        // Assert
        assertThat(usuario.getRol()).isEqualTo(Rol.EMPLEADO);
        assertThat(usuario.getClave()).isEqualTo("hashedClave");
        verify(usuarioPersistencePort, times(1)).guardarUsuario(usuario);
    }
    //Negative path: Guardar empleado con fecha de nacimiento inválida (menor de edad)
    @Test
    void testGuardarEmpleado_fechaNacimientoMenorDe18() {
        Usuario usuario = new Usuario();
        usuario.setClave("clave");
        usuario.setFechaNacimiento(LocalDate.of(2024, 8, 9));
        // Act + Assert
        assertThrows(UsuarioNoMayorEdadException.class, () -> {
            usuarioUseCase.crearUsuarioEmpleado(usuario, 1L);
        });
        // Verificar que no se guarde el usuario porque la fecha de nacimiento es inválida
        verify(usuarioPersistencePort, never()).guardarUsuario(any());
    }
    //Happy path: Guardar Cliente con clave hasheada y rol cambiado
    @Test
    void testGuardarCliente_hashearClaveYCambiarRol() {
        //Arrange
        Usuario usuario = new Usuario();
        usuario.setClave("clave");
        usuario.setFechaNacimiento(LocalDate.of(1996, 8, 9));
        when(passwordEncoderPort.encode("clave")).thenReturn("hashedClave");
        //Act
        usuarioUseCase.crearUsuarioCliente(usuario);
        // Assert
        assertThat(usuario.getRol()).isEqualTo(Rol.CLIENTE);
        assertThat(usuario.getClave()).isEqualTo("hashedClave");
        verify(usuarioPersistencePort, times(1)).guardarUsuario(usuario);
    }
    //Negative path: Guardar Cliente con fecha de nacimiento inválida (menor de edad)
    @Test
    void testGuardarCliente_fechaNacimientoMenorDe18() {
        Usuario usuario = new Usuario();
        usuario.setClave("clave");
        usuario.setFechaNacimiento(LocalDate.of(2024, 8, 9));
        // Act + Assert
        assertThrows(UsuarioNoMayorEdadException.class, () -> {
            usuarioUseCase.crearUsuarioCliente(usuario);
        });
        // Verificar que no se guarde el usuario porque la fecha de nacimiento es inválida
        verify(usuarioPersistencePort, never()).guardarUsuario(any());
    }
}
