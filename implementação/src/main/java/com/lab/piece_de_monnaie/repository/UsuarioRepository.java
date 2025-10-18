package com.lab.piece_de_monnaie.repository;

import com.lab.piece_de_monnaie.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
