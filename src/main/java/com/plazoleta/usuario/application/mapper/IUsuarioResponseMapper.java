package com.plazoleta.usuario.application.mapper;

import com.plazoleta.usuario.application.dto.UsuarioResponse;
import com.plazoleta.usuario.domain.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUsuarioResponseMapper {
    UsuarioResponse toDto(Usuario domain);
    Usuario toDomain(UsuarioResponse dto);
}
