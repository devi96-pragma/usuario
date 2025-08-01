package com.plazoleta.usuario.application.mapper;

import com.plazoleta.usuario.application.dto.EmpleadoRequestDto;
import com.plazoleta.usuario.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEmpleadoRequestMapper {
    Usuario toDomain(EmpleadoRequestDto dto);
}
