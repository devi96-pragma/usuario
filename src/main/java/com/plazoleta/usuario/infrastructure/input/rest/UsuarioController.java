package com.plazoleta.usuario.infrastructure.input.rest;

import com.plazoleta.usuario.application.dto.EmpleadoRequestDto;
import com.plazoleta.usuario.application.dto.LoginResponseDto;
import com.plazoleta.usuario.application.dto.UsuarioRequestDto;
import com.plazoleta.usuario.application.dto.UsuarioResponseDto;
import com.plazoleta.usuario.application.handler.IUsuarioHandler;
import com.plazoleta.usuario.domain.model.Rol;
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

    @Operation(summary = "Crear un nuevo usuario propietario", description = "Registra un nuevo propietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Propietario creado exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping("/propietarios")
    public ResponseEntity<Void> crearPropietario(@RequestBody @Valid @Parameter(description = "Datos del nuevo propietario") UsuarioRequestDto request){
        usuarioHandler.crearPropietario(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Crear un nuevo usuario empleado",
            description = "Registra un nuevo empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Documento o correo ya registrado", content = @Content)
    })
    @PostMapping("/empleados")
    public ResponseEntity<Void> crearEmpleado(
            @RequestBody @Valid @Parameter(description = "Datos del nuevo Empleado", required = true)
            EmpleadoRequestDto request){
        usuarioHandler.crearEmpleado(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Buscar propietario por ID", description = "Devuelve la información del propietario dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propietario encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Propietario no encontrado", content = @Content)
    })
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponseDto> buscarUsuarioPorId(@PathVariable @Parameter(description = "ID del usuario a buscar") Long idUsuario) {
        UsuarioResponseDto response = usuarioHandler.buscarUsuarioPorId(idUsuario);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Crear un nuevo usuario cliente",
            description = "Registra un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Documento o correo ya registrado", content = @Content)
    })
    @PostMapping("/clientes")
    public ResponseEntity<Void> crearCliente(
            @RequestBody @Valid @Parameter(description = "Datos del nuevo Cliente", required = true)
            UsuarioRequestDto request){
        usuarioHandler.crearCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
