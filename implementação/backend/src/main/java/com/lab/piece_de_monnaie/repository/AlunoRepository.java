package com.lab.piece_de_monnaie.repository;

import com.lab.piece_de_monnaie.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    
    boolean existsByCpf(String cpf);
    
    List<Aluno> findByCursoId(Long cursoId);
    
    Optional<Aluno> findByCpf(String cpf);
}
