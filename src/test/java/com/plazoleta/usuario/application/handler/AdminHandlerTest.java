package com.plazoleta.usuario.application.handler;

import com.plazoleta.usuario.application.dto.UsuarioRequestDto;
import com.plazoleta.usuario.application.dto.UsuarioResponseDto;
import com.plazoleta.usuario.application.mapper.IUsuarioRequestMapper;
import com.plazoleta.usuario.domain.api.IUsuarioServicePort;
import com.plazoleta.usuario.domain.model.Rol;
import com.plazoleta.usuario.domain.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminHandlerTest {
    @Mock
    private IUsuarioServicePort servicePort;
    @Mock
    private IUsuarioRequestMapper mapper;
    @InjectMocks
    private UsuarioHandler adminHandler;

    //HappyPath: crearPropietario con exito
    @Test
    public void crearPropietario_ConPropietario_Exitoso() {
        // Arrange
        // Aquí puedes configurar el mock de servicePort y mapper según sea necesario
        UsuarioRequestDto dto =  new  UsuarioRequestDto(
                "Harold",
                "Gomez",
                12345678,
                "+519876543210",
                LocalDate.of(1995,8, 9),
                "harold.gomez@example.com",
                "miClaveSegura123"
        );
        Usuario usuario = new Usuario(
                null, // ID se asignará al guardar
                null, // Rol se asignará en el use case
                "miClaveSegura123",
                "harold.gomez@example.com",
                LocalDate.of(1995,8, 9),
                "+51960259627",
                12345678,
                "Gomez",
                "Harold");
        when(mapper.toDomain(dto)).thenReturn(usuario);
        // Act
        adminHandler.crearPropietario(dto);

        // Assert
        Mockito.verify(servicePort,times(1))
                .crearUsuarioPropietario(usuario);
    }
    // HappyPath: buscarPropietarioPorId con éxito
    @Test
    public void buscarPropietarioPorId_conDataValida_Exitoso() {
        // Arrange
        Long idUsuario = 1L;
        Usuario usuario = new Usuario();
        usuario.setRol(com.plazoleta.usuario.domain.model.Rol.PROPIETARIO);
        when(servicePort.buscarUsuarioPorId(idUsuario)).thenReturn(usuario);

        UsuarioResponseDto responseDto = new UsuarioResponseDto();
        when(mapper.toResponse(usuario)).thenReturn(responseDto);

        // Act
        UsuarioResponseDto result = adminHandler.buscarUsuarioPorId(idUsuario);

        // Assert
        Mockito.verify(servicePort, times(1)).buscarUsuarioPorId(idUsuario);
        Mockito.verify(mapper, times(1)).toResponse(usuario);
        org.junit.jupiter.api.Assertions.assertEquals(responseDto, result);
    }


}
