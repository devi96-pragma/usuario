package com.plazoleta.usuario.application.dto;

import jakarta.persistence.UniqueConstraint;
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
public class UsuarioRequestDto {
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotNull(message = "El documento es obligatorio")
    @Digits(integer = 20, fraction = 0, message = "El documento debe ser numérico")
    private Integer documentoDeIdentidad;
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
}
