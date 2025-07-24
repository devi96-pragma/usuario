package com.plazoleta.usuario.domain.usecase;

import com.plazoleta.usuario.domain.api.IPasswordEncoderPort;
import com.plazoleta.usuario.domain.model.Rol;
import com.plazoleta.usuario.domain.model.Usuario;
import com.plazoleta.usuario.domain.spi.IUsuarioPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class UsuarioUseCaseTest {

    @Mock
    private IUsuarioPersistencePort usuarioPersistencePort;
    @Mock
    private IPasswordEncoderPort passwordEncoderPort;
    @InjectMocks
    private UsuarioUseCase usuarioUseCase;


    @Test
    void testGuardarPropietario_hashearClaveYCambiarRol() {
        // Arrange
        Usuario usuario = new Usuario(
                1L,
                Rol.PROPIETARIO,
                "clave",
                "harold@gmail.com",
                LocalDate.of(1996,8, 9),
                "+51960259627",
                123456787,
                "Juan",
                "Pérez"); // completa según tu modelo
        when(passwordEncoderPort.encode("clave")).thenReturn("hashedClave");

        // Act
        usuarioUseCase.crearPropietario(usuario);

        // Assert
        assertThat(usuario.getClave()).isNotEqualTo("clave");
        assertThat(usuario.getRol()).isEqualTo(Rol.PROPIETARIO);
        verify(usuarioPersistencePort, times(1)).guardarPropietario(usuario);
    }
}
