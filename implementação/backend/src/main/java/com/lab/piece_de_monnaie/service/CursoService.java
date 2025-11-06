package com.lab.piece_de_monnaie.service;

import com.lab.piece_de_monnaie.entity.Curso;
import com.lab.piece_de_monnaie.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> findAll(){
        return cursoRepository.findAll();
    }
}
