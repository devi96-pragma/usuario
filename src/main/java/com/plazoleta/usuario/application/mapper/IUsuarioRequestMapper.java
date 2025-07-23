package com.plazoleta.usuario.application.mapper;

import com.plazoleta.usuario.application.dto.UsuarioRequest;
import com.plazoleta.usuario.domain.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUsuarioRequestMapper {
    Usuario toDomain(UsuarioRequest dto);
    UsuarioRequest toDto(Usuario domain);
}
