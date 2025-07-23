package com.plazoleta.usuario.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class UsuarioRequest {
    private String nombre;
    private String apellido;
    private int documentoDeIdentidad;
    private String celular;
    private LocalDate fechaNacimiento;
    private String correo;
    private String clave;
}
