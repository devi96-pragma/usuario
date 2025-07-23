package com.plazoleta.usuario.infrastructure.out.jpa.repository;

import com.plazoleta.usuario.domain.model.Usuario;
import com.plazoleta.usuario.infrastructure.out.jpa.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
}
