package com.plazoleta.usuario.infrastructure.out.jwt.adapter;

import com.plazoleta.usuario.domain.api.ITokenServicePort;
import com.plazoleta.usuario.domain.model.Usuario;
import com.plazoleta.usuario.infrastructure.out.jwt.config.JwtConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
@Slf4j
@Component
public class JwtTokenAdapter implements ITokenServicePort {

    private final JwtConfig jwtConfig;

    public JwtTokenAdapter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }
    @Override
    public String generateToken(Usuario usuario, List<String> roles) {
        log.info("Generating JWT token for user: {}", usuario.getCorreo());
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usuario.getId());
        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getCorreo())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
                 SignatureException | IllegalArgumentException e) {
            return false;
        }
    }
    @Override
    public String getUsernameFromToken(String token) {
        return parseToken(token).getBody().getSubject();
    }
    public List<GrantedAuthority> getAuthorities(String token) {
        Claims claims = parseToken(token).getBody();
        List<String> roles = (List<String>) claims.get("roles");
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getSecret().getBytes())
                .build()
                .parseClaimsJws(token);
    }
}
