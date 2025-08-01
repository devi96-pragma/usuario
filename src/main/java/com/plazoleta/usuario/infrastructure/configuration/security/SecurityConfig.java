package com.plazoleta.usuario.infrastructure.configuration.security;

import com.plazoleta.usuario.infrastructure.out.jwt.adapter.JwtTokenAdapter;
import com.plazoleta.usuario.infrastructure.out.jwt.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml"
                        ).permitAll()  // 👈 Swagger libre
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios/clientes").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios/propietarios").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/usuarios/empleados").hasRole("PROPIETARIO")
                        .requestMatchers(HttpMethod.GET, "/usuarios/*").hasAnyRole("ADMINISTRADOR","PROPIETARIO")
                        .anyRequest().authenticated()
                )
                .httpBasic(AbstractHttpConfigurer::disable)  // Desactiva login por defecto
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
