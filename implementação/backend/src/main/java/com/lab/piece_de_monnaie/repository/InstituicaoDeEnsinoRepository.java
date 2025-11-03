package com.lab.piece_de_monnaie.repository;

import com.lab.piece_de_monnaie.entity.InstituicaoDeEnsino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstituicaoDeEnsinoRepository extends JpaRepository<InstituicaoDeEnsino, Long> {
    Optional<InstituicaoDeEnsino> findByCnpj(String cnpj);
}
