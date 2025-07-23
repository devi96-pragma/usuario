package com.plazoleta.usuario.domain.model;

import java.time.LocalDate;

public class Usuario {
    private long id;
    private String nombre;
    private String apellido;
    private int documentoDeIdentidad;
    private String celular;
    private LocalDate fechaNacimiento;
    private String correo;
    private String clave;
}
