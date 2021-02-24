package com.eterna.server.core.domain.repositories;

import com.eterna.server.core.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, String> {
  Usuario findByUsuario(String usuario);
}
