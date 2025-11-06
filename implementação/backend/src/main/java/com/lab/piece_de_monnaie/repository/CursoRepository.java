package com.lab.piece_de_monnaie.repository;

import com.lab.piece_de_monnaie.entity.Curso;
import com.lab.piece_de_monnaie.entity.InstituicaoDeEnsino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Optional<Curso> findByNomeAndInstituicaoDeEnsino(String nome, InstituicaoDeEnsino instituicaoDeEnsino);
}
