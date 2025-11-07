package com.lab.piece_de_monnaie.service;

import com.lab.piece_de_monnaie.entity.Curso;
import com.lab.piece_de_monnaie.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    public List<Curso> findAll(){
        return cursoRepository.findAll();
    }
}
