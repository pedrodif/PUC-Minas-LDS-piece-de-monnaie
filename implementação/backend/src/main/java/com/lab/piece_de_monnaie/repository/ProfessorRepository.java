package com.lab.piece_de_monnaie.repository;

import com.lab.piece_de_monnaie.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
