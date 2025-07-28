package com.plazoleta.usuario.infrastructure.configuration;

import com.plazoleta.usuario.domain.api.IAuthServicePort;
import com.plazoleta.usuario.domain.api.ITokenServicePort;
import com.plazoleta.usuario.domain.spi.IPasswordEncoderPort;
import com.plazoleta.usuario.domain.api.IUsuarioServicePort;
import com.plazoleta.usuario.domain.spi.IUsuarioPersistencePort;
import com.plazoleta.usuario.domain.usecase.AuthUseCase;
import com.plazoleta.usuario.domain.usecase.UsuarioUseCase;
import com.plazoleta.usuario.infrastructure.out.bcrypt.adapter.BCryptPasswordEncoderAdapter;
import com.plazoleta.usuario.infrastructure.out.jpa.adapter.UsuarioJpaAdapter;
import com.plazoleta.usuario.infrastructure.out.jpa.mapper.IUsuarioEntityMapper;
import com.plazoleta.usuario.infrastructure.out.jpa.repository.IUsuarioRepository;
import com.plazoleta.usuario.infrastructure.out.jwt.adapter.JwtTokenAdapter;
import com.plazoleta.usuario.infrastructure.out.jwt.config.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IUsuarioRepository iUsuarioRepository;
    private final IUsuarioEntityMapper iUsuarioEntityMapper;

    @Bean
    public IUsuarioPersistencePort usuarioPersistencePort() {
        return new UsuarioJpaAdapter(iUsuarioRepository, iUsuarioEntityMapper);
    }
    @Bean
    public IUsuarioServicePort usuarioServicePort(
            IUsuarioPersistencePort usuarioPersistencePort,
            IPasswordEncoderPort encoderPort,
            ITokenServicePort tokenServicePort){
        return new UsuarioUseCase(usuarioPersistencePort, encoderPort, tokenServicePort);
    }
    @Bean
    public IPasswordEncoderPort passwordEncoderPort() {
        return new BCryptPasswordEncoderAdapter(new BCryptPasswordEncoder());
    }
    @Bean
    public ITokenServicePort tokenServicePort(JwtConfig jwtConfig) {
        return new JwtTokenAdapter(jwtConfig);
    }
    @Bean
    public IAuthServicePort authServicePort(
            IUsuarioPersistencePort usuarioPersistencePort,
            ITokenServicePort tokenServicePort,
            IPasswordEncoderPort encoderPort) {
        return new AuthUseCase(
                tokenServicePort,
                usuarioPersistencePort,
                encoderPort
        );
    }
}
