package com.plazoleta.usuario.infrastructure.configuration;

import com.plazoleta.usuario.domain.api.IPasswordEncoderPort;
import com.plazoleta.usuario.domain.api.IUsuarioServicePort;
import com.plazoleta.usuario.domain.spi.IUsuarioPersistencePort;
import com.plazoleta.usuario.domain.usecase.UsuarioUseCase;
import com.plazoleta.usuario.infrastructure.out.jpa.adapter.BCryptPasswordEncoderAdapter;
import com.plazoleta.usuario.infrastructure.out.jpa.adapter.UsuarioJpaAdapter;
import com.plazoleta.usuario.infrastructure.out.jpa.mapper.IUsuarioEntityMapper;
import com.plazoleta.usuario.infrastructure.out.jpa.repository.IUsuarioRepository;
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
    public IUsuarioServicePort usuarioServicePort(IPasswordEncoderPort encoderPort){
        return new UsuarioUseCase(usuarioPersistencePort(), encoderPort);
    }
    @Bean
    public IPasswordEncoderPort passwordEncoderPort() {
        return new BCryptPasswordEncoderAdapter(new BCryptPasswordEncoder());
    }
}
