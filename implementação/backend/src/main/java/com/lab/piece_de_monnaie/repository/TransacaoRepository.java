package com.lab.piece_de_monnaie.repository;

import com.lab.piece_de_monnaie.entity.Transacao;
import com.lab.piece_de_monnaie.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    @Query("SELECT t FROM Transacao t " +
            "WHERE t.emissor.username = :username " +
            "OR t.receptor.username = :username")
    List<Transacao> findAllByEmissorOrReceptorUsername(@Param("username") String username);
}
