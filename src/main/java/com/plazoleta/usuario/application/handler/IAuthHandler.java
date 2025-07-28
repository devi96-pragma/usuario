package com.plazoleta.usuario.application.handler;

import com.plazoleta.usuario.application.dto.LoginRequestDto;

public interface IAuthHandler {
    String login(LoginRequestDto loginRequestDto);
}
