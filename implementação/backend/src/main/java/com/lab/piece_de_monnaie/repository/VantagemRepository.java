package com.lab.piece_de_monnaie.repository;

import com.lab.piece_de_monnaie.entity.Vantagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VantagemRepository extends JpaRepository<Vantagem, Long> {
    List<Vantagem> findAllByEmpresaParceiraId(Long empresaParceiraId);
}
