package com.lab.piece_de_monnaie.repository;

import com.lab.piece_de_monnaie.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
