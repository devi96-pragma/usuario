package com.plazoleta.usuario.domain.spi;

import com.plazoleta.usuario.domain.model.EmpleadoCreadoEvento;

public interface IEmpleadoEventPublisherPort {
    void publicarEmpleadoCreado(EmpleadoCreadoEvento evento);
}
