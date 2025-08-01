package com.plazoleta.usuario.domain.constantes;

public final class Constantes {
    private Constantes() {
    }
    public static final class MensajesError{
        public static final String CREDENCIALES_INVALIDAS = "Credenciales inválidas";
        public static final String USUARIO_NO_ENCONTRADO = "Usuario no encontrado";
        public static final String USUARIO_EXISTE = "El usuario ya existe";
        public static final String USUARIO_NO_MAYOR_EDAD = "El usuario debe ser mayor de edad";
        public static final String USUARIO_SIN_FECHA_NACIMIENTO = "La fecha de nacimiento es obligatoria";
        public static final String USUARIO_NO_ES_PROPIETARIO = "El usuario no es propietario o no existe";
    }

    public static final class Validacion{
        public static final int EDAD_MINIMA = 18;
    }

    public static final class Paginacion {
        public static final int TAMANIO_DEFECTO = 10;
        public static final int PAGINA_DEFECTO = 0;
    }

}
