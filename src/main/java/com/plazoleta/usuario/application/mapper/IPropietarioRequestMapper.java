package com.plazoleta.usuario.application.mapper;

import com.plazoleta.usuario.application.dto.PropietarioRequestDto;
import com.plazoleta.usuario.application.dto.PropietarioResponseDto;
import com.plazoleta.usuario.domain.model.Rol;
import com.plazoleta.usuario.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPropietarioRequestMapper {

    @Mapping(target = "rol", expression = "java(com.plazoleta.usuario.domain.model.Rol.PROPIETARIO)")
    @Mapping(target = "id", ignore = true)
    Usuario toDomain(PropietarioRequestDto dto);
    PropietarioResponseDto toResponse(Usuario usuario);
}
