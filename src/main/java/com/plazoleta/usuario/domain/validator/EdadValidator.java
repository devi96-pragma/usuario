package com.plazoleta.usuario.domain.validator;

import com.plazoleta.usuario.domain.constantes.Constantes;
import com.plazoleta.usuario.domain.exception.UsuarioNoMayorEdadException;
import com.plazoleta.usuario.domain.exception.UsuarioSinFechaNacimientoException;
import org.apache.tomcat.util.bcel.Const;

import java.time.LocalDate;
import java.time.Period;

public class EdadValidator {
    public static void validarMayorDeEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new UsuarioSinFechaNacimientoException(Constantes.MensajesError.USUARIO_SIN_FECHA_NACIMIENTO);
        }

        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if (edad < Constantes.Validacion.EDAD_MINIMA) {
            throw new UsuarioNoMayorEdadException(Constantes.MensajesError.USUARIO_NO_MAYOR_EDAD);
        }
    }
}
