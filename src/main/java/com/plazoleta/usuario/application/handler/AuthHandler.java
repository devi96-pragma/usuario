package com.plazoleta.usuario.application.handler;

import com.plazoleta.usuario.application.dto.LoginRequestDto;
import com.plazoleta.usuario.domain.api.IAuthServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthHandler implements IAuthHandler{
    private final IAuthServicePort authServicePort;

    @Override
    public String login(LoginRequestDto loginRequestDto) {
        return authServicePort.login(
                loginRequestDto.getUsername(),
                loginRequestDto.getPassword()
        );
    }
}
