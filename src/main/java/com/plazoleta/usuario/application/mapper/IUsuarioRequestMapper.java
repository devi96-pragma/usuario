package com.plazoleta.usuario.application.mapper;

import com.plazoleta.usuario.application.dto.UsuarioRequestDto;
import com.plazoleta.usuario.application.dto.UsuarioResponseDto;
import com.plazoleta.usuario.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUsuarioRequestMapper {
    //@Mapping(target = "rol", expression = "java(com.plazoleta.usuario.domain.model.Rol.PROPIETARIO)")
    @Mapping(target = "id", ignore = true)
    Usuario toDomain(UsuarioRequestDto dto);
    UsuarioResponseDto toResponse(Usuario usuario);
}
