package com.plazoleta.usuario.infrastructure.out.jpa.mapper;

import com.plazoleta.usuario.domain.model.Usuario;
import com.plazoleta.usuario.infrastructure.out.jpa.entity.UsuarioEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUsuarioEntityMapper {
    UsuarioEntity toEntity(Usuario usuario);
    Usuario toDomain(UsuarioEntity usuarioEntity);
    List<Usuario> toUsuarioList(List<UsuarioEntity> usuarioEntities);
}
