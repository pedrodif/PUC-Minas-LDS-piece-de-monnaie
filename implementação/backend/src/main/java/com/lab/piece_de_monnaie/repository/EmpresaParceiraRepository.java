package com.lab.piece_de_monnaie.repository;

import com.lab.piece_de_monnaie.entity.EmpresaParceira;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaParceiraRepository extends JpaRepository<EmpresaParceira, Long> {
    
    boolean existsByCnpj(String cnpj);
    
    boolean existsByEmail(String email);
    
    Optional<EmpresaParceira> findByCnpj(String cnpj);
    
    Optional<EmpresaParceira> findByEmail(String email);
}