package com.plazoleta.usuario.application.dto;

import com.plazoleta.usuario.domain.model.Rol;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PropietarioResponseDto {
    private Long id;
    private String nombre;
    private String apellido;
    private int documentoDeIdentidad;
    private String celular;
    private LocalDate fechaNacimiento;
    private String correo;
    private Rol rol;
}
