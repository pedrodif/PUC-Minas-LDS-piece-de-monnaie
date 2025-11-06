package com.lab.piece_de_monnaie.service;

import com.lab.piece_de_monnaie.entity.Vantagem;
import com.lab.piece_de_monnaie.repository.VantagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VantagemService {

    private final VantagemRepository vantagemRepository;
    public VantagemService(VantagemRepository vantagemRepository) {
        this.vantagemRepository = vantagemRepository;
    }

    public List<Vantagem> findAll(){
        return vantagemRepository.findAll();
    }

    public Optional<Vantagem> findById(Long id){
        return vantagemRepository.findById(id);
    }
}
