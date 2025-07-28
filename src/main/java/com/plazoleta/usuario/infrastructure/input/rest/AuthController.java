package com.plazoleta.usuario.infrastructure.input.rest;

import com.plazoleta.usuario.application.dto.LoginRequestDto;
import com.plazoleta.usuario.application.dto.LoginResponseDto;
import com.plazoleta.usuario.application.handler.AuthHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthHandler authHandler;
    @Operation(summary = "Autenticar usuario", description = "Devuelve un JWT si las credenciales son válidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody @Parameter(description = "Credenciales de login") LoginRequestDto user) {
        String token = authHandler.login(user);
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

}
