package com.lab.piece_de_monnaie.service;

import com.lab.piece_de_monnaie.dto.VantagemRequest;
import com.lab.piece_de_monnaie.entity.Vantagem;
import com.lab.piece_de_monnaie.exception.ResourceNotFoundException;
import com.lab.piece_de_monnaie.mapper.VantagemMapper;
import com.lab.piece_de_monnaie.repository.VantagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VantagemService {

    private final VantagemRepository vantagemRepository;
    private final VantagemMapper vantagemMapper;

    public List<Vantagem> findAll(){
        return vantagemRepository.findAll();
    }

    public Optional<Vantagem> findById(Long id){
        return vantagemRepository.findById(id);
    }

    public Vantagem update(VantagemRequest vantagemRequest, Long vantagemId){
        Vantagem vantagem = findById(vantagemId)
                .orElseThrow(() -> new ResourceNotFoundException("Vantagem de id " + vantagemId + " não encontrada."));

        Vantagem vantagemAtualizada = vantagemMapper.updateVantagem(vantagemRequest,  vantagem);
        return vantagemRepository.save(vantagemAtualizada);
    }

    public void deleteById(Long id){
        vantagemRepository.deleteById(id);
    }
}
