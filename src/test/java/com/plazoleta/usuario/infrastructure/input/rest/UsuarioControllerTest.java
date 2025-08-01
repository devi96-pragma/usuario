package com.plazoleta.usuario.infrastructure.input.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.plazoleta.usuario.application.dto.EmpleadoRequestDto;
import com.plazoleta.usuario.application.dto.UsuarioRequestDto;
import com.plazoleta.usuario.application.dto.UsuarioResponseDto;
import com.plazoleta.usuario.application.handler.IUsuarioHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IUsuarioHandler usuarioHandler;

    //Happy path para crear propietario
    @Test
    void crearPropietario_conDatosValidos_retornaCreado() throws Exception {
        UsuarioRequestDto requestDto = new UsuarioRequestDto();
        requestDto.setNombre("Juan");
        requestDto.setApellido("Perez");
        requestDto.setCorreo("juan.perez@email.com");
        requestDto.setClave("clave");
        requestDto.setFechaNacimiento(LocalDate.of(1996,8,12));
        requestDto.setDocumentoDeIdentidad(111111112);
        requestDto.setCelular("+51590726524");
        doNothing().when(usuarioHandler).crearPropietario(any(UsuarioRequestDto.class));
        //Act
        mockMvc.perform(post("/usuarios/propietarios")
                        .contentType("application/json")
                        .content(asJson(requestDto)))
                .andExpect(status().isCreated());
    }
    //Happy path para crear empleado
    @Test
    void crearEmpleado_conDatosValidos_retornaCreado() throws Exception {
        EmpleadoRequestDto requestDto = new EmpleadoRequestDto();
        requestDto.setNombre("Juan");
        requestDto.setApellido("Perez");
        requestDto.setCorreo("juan.perez@email.com");
        requestDto.setClave("clave");
        requestDto.setFechaNacimiento(LocalDate.of(1996,8,12));
        requestDto.setDocumentoDeIdentidad(111111112);
        requestDto.setCelular("+51590726524");
        requestDto.setIdRestaurante(2L);
        doNothing().when(usuarioHandler).crearEmpleado(requestDto);

        //Act
        mockMvc.perform(post("/usuarios/empleados")
                        .contentType("application/json")
                        .content(asJson(requestDto)))
                .andExpect(status().isCreated());
        verify(usuarioHandler,times(1)).crearEmpleado(any(EmpleadoRequestDto.class));
    }
    //Happy path para crear cliente
    @Test
    void crearCliente_conDatosValidos_retornaCreado() throws Exception {
        UsuarioRequestDto requestDto = new UsuarioRequestDto();
        requestDto.setNombre("Juan");
        requestDto.setApellido("Perez");
        requestDto.setCorreo("juan.perez@email.com");
        requestDto.setClave("clave");
        requestDto.setFechaNacimiento(LocalDate.of(1996,8,12));
        requestDto.setDocumentoDeIdentidad(111111112);
        requestDto.setCelular("+51590726524");

        doNothing().when(usuarioHandler).crearCliente(requestDto);

        //Act
        mockMvc.perform(post("/usuarios/clientes")
                        .contentType("application/json")
                        .content(asJson(requestDto)))
                .andExpect(status().isCreated());
        verify(usuarioHandler,times(1)).crearCliente(any(UsuarioRequestDto.class));
    }

    //Happy para buscar usuario por ID
    @Test
    void buscarUsuarioPorId_conIdValido_retornaUsuario() throws Exception {
        Long idUsuario = 1L;
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(idUsuario);

        when(usuarioHandler.buscarUsuarioPorId(idUsuario)).thenReturn(dto);

        mockMvc.perform(get("/usuarios/{idUsuario}", idUsuario)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        verify(usuarioHandler, times(1)).buscarUsuarioPorId(idUsuario);
    }

    // Helper para convertir a JSON
    private String asJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // <-- aquí
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // opcional, pero recomendable
        return mapper.writeValueAsString(obj);
    }
}
