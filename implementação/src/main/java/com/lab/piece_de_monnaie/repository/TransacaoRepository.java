package com.lab.piece_de_monnaie.repository;

import com.lab.piece_de_monnaie.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
