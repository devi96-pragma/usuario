package com.plazoleta.usuario.application.dto;

import jakarta.validation.constraints.*;
import lombok.*;


import java.time.LocalDate;


public class PropietarioRequestDto{
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotNull
    @Digits(integer = 20, fraction = 0)
    private int documentoDeIdentidad;
    @NotNull
    @Pattern(regexp = "^\\+?[0-9]{9,13}$", message = "Número de celular inválido")
    private String celular;
    @NotNull
    private LocalDate fechaNacimiento;
    @NotBlank
    @Email
    private String correo;
    @NotBlank
    private String clave;

    public PropietarioRequestDto() {
    }

    public PropietarioRequestDto(String nombre, String apellido, int documentoDeIdentidad, String celular, LocalDate fechaNacimiento, String correo, String clave) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documentoDeIdentidad = documentoDeIdentidad;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDocumentoDeIdentidad() {
        return documentoDeIdentidad;
    }

    public void setDocumentoDeIdentidad(int documentoDeIdentidad) {
        this.documentoDeIdentidad = documentoDeIdentidad;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
