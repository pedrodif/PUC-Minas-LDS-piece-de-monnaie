package com.lab.piece_de_monnaie.controller;

import com.lab.piece_de_monnaie.dto.CursoResponse;
import com.lab.piece_de_monnaie.mapper.CursoMapper;
import com.lab.piece_de_monnaie.service.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;
    private final CursoMapper cursoMapper;
    public CursoController(CursoService cursoService,
                           CursoMapper cursoMapper){
        this.cursoService = cursoService;
        this.cursoMapper = cursoMapper;
    }

    @GetMapping
    public ResponseEntity<List<CursoResponse>> findAll(){
        return ResponseEntity.ok(cursoService.findAll().stream().map(cursoMapper::toCursoResponse).toList());
    }
}
