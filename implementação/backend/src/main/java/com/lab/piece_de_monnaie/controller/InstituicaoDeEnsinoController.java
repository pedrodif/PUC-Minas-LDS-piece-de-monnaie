package com.lab.piece_de_monnaie.controller;

import com.lab.piece_de_monnaie.dto.CursoResponse;
import com.lab.piece_de_monnaie.dto.InstituicaoDeEnsinoResponse;
import com.lab.piece_de_monnaie.mapper.CursoMapper;
import com.lab.piece_de_monnaie.mapper.InstituicaoDeEnsinoMapper;
import com.lab.piece_de_monnaie.service.InstituicaoDeEnsinoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/instituicoes")
public class InstituicaoDeEnsinoController {

    private final InstituicaoDeEnsinoService instituicaoDeEnsinoService;
    private final InstituicaoDeEnsinoMapper instituicaoDeEnsinoMapper;

    public InstituicaoDeEnsinoController(InstituicaoDeEnsinoService instituicaoDeEnsinoService,
                                         InstituicaoDeEnsinoMapper instituicaoDeEnsinoMapper) {
        this.instituicaoDeEnsinoService = instituicaoDeEnsinoService;
        this.instituicaoDeEnsinoMapper = instituicaoDeEnsinoMapper;
    }

    @GetMapping
    public ResponseEntity<List<InstituicaoDeEnsinoResponse>> findAll() {
        return ResponseEntity
                .ok(instituicaoDeEnsinoService.findAll().stream()
                        .map(instituicaoDeEnsinoMapper::toInstituicaoDeEnsinoResponse)
                        .toList());
    }

}
