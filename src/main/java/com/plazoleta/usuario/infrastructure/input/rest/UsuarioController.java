package com.plazoleta.usuario.infrastructure.input.rest;

import com.plazoleta.usuario.application.dto.LoginResponseDto;
import com.plazoleta.usuario.application.dto.PropietarioRequestDto;
import com.plazoleta.usuario.application.dto.PropietarioResponseDto;
import com.plazoleta.usuario.application.handler.IUsuarioHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final IUsuarioHandler usuarioHandler;
    @Operation(summary = "Crear un nuevo usuario propietario", description = "Registra un nuevo propietario y devuelve un token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Propietario creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping("/propietarios")
    public ResponseEntity<LoginResponseDto> crearPropietario(@RequestBody @Valid @Parameter(description = "Datos del nuevo propietario") PropietarioRequestDto request){
        String token = usuarioHandler.crearUsuarioPropietario(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new LoginResponseDto(token));
    }
    @Operation(summary = "Buscar propietario por ID", description = "Devuelve la información del propietario dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propietario encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PropietarioResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Propietario no encontrado", content = @Content)
    })
    @GetMapping("/{idUsuario}")
    public ResponseEntity<PropietarioResponseDto> buscarUsuarioPorId(@PathVariable @Parameter(description = "ID del usuario a buscar") Long idUsuario) {
        PropietarioResponseDto response = usuarioHandler.buscarUsuarioPorId(idUsuario);
        return ResponseEntity.ok(response);
    }
}
