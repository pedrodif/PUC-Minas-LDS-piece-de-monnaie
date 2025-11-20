package com.lab.piece_de_monnaie.repository;

import com.lab.piece_de_monnaie.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    @Transactional(readOnly = true)
    Optional<Professor> findByUsername(String username);
}
