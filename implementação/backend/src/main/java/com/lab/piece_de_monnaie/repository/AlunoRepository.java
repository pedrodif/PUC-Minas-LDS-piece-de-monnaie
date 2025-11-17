package com.lab.piece_de_monnaie.repository;

import com.lab.piece_de_monnaie.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    
    boolean existsByCpf(String cpf);
    
    List<Aluno> findByCursoId(Long cursoId);
    
    Optional<Aluno> findByCpf(String cpf);
    
    @Query("SELECT a FROM Aluno a WHERE a.curso.departamento = :departamento")
    List<Aluno> findByCursoDepartamento(@Param("departamento") String departamento);
}
