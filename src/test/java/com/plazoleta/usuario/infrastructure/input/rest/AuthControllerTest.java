package com.plazoleta.usuario.infrastructure.input.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plazoleta.usuario.application.dto.LoginRequestDto;
import com.plazoleta.usuario.application.handler.AuthHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AuthHandler authHandler;

    @Test
    void authenticate_conCredencialesValidas_retornaToken() throws Exception {
        // Arrange
        LoginRequestDto loginRequest = new LoginRequestDto("usuario", "password");
        String tokenFalso = "ey.fake.jwt.token";

        when(authHandler.login(any(LoginRequestDto.class))).thenReturn(tokenFalso);

        // Act + Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(tokenFalso));
    }

    // Helper para convertir a JSON
    private String asJson(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
