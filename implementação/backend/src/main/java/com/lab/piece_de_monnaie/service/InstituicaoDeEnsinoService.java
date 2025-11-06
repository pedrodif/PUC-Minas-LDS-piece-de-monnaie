package com.lab.piece_de_monnaie.service;

import com.lab.piece_de_monnaie.entity.Curso;
import com.lab.piece_de_monnaie.entity.InstituicaoDeEnsino;
import com.lab.piece_de_monnaie.repository.CursoRepository;
import com.lab.piece_de_monnaie.repository.InstituicaoDeEnsinoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituicaoDeEnsinoService {

    public InstituicaoDeEnsinoRepository InstituicaoDeEnsinoRepository;
    public CursoRepository cursoRepository;
    public InstituicaoDeEnsinoService(InstituicaoDeEnsinoRepository InstituicaoDeEnsinoRepository,
                                      CursoRepository cursoRepository) {
        this.InstituicaoDeEnsinoRepository = InstituicaoDeEnsinoRepository;
        this.cursoRepository = cursoRepository;
    }

    public List<InstituicaoDeEnsino> findAll(){
        return InstituicaoDeEnsinoRepository.findAll();
    }

}
