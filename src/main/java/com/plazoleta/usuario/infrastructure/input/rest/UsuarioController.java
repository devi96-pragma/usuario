package com.plazoleta.usuario.infrastructure.input.rest;

import com.plazoleta.usuario.application.dto.PropietarioRequestDto;
import com.plazoleta.usuario.application.handler.IAdminHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor

public class UsuarioController {

    private final IAdminHandler adminHandler;
    private final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @PostMapping("/propietarios")
    public ResponseEntity<Void> crearPropietario(@RequestBody PropietarioRequestDto request){
        log.info("Creando propietario: {}", request);
        log.info("Request: {}", request.getFechaNacimiento());
        adminHandler.crearPropietario(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
