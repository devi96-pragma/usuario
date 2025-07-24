package com.plazoleta.usuario.domain.spi;

import com.plazoleta.usuario.domain.model.Usuario;

import java.util.List;

public interface IUsuarioPersistencePort {
    void guardarPropietario(Usuario usuario);
}
