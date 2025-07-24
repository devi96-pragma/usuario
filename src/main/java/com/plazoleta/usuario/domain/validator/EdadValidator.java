package com.plazoleta.usuario.domain.validator;

import com.plazoleta.usuario.infrastructure.exception.UsuarioNoMayorEdadException;
import com.plazoleta.usuario.infrastructure.exception.UsuarioSinFechaNacimientoException;

import java.time.LocalDate;
import java.time.Period;

public class EdadValidator {
    public static void validarMayorDeEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new UsuarioSinFechaNacimientoException("La fecha de nacimiento es obligatoria");
        }

        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if (edad < 18) {
            throw new UsuarioNoMayorEdadException("El usuario debe ser mayor de edad");
        }
    }
}
