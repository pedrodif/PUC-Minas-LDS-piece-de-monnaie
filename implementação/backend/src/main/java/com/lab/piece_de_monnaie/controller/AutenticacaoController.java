package com.lab.piece_de_monnaie.controller;

import com.lab.piece_de_monnaie.dto.*;
import com.lab.piece_de_monnaie.dto.auth.LoginRequest;
import com.lab.piece_de_monnaie.dto.auth.LoginResponse;
import com.lab.piece_de_monnaie.service.AlunoService;
import com.lab.piece_de_monnaie.service.AutenticacaoService;
import com.lab.piece_de_monnaie.service.EmpresaParceiraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;
    private final AlunoService alunoService;
    private final EmpresaParceiraService empresaParceiraService;

    @PostMapping("/aluno")
    public ResponseEntity<AlunoDTO> create(@Valid @RequestBody CreateAlunoDTO createAlunoDTO) {
        AlunoDTO aluno = alunoService.create(createAlunoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(aluno);
    }

    @PostMapping("/parceiro")
    public ResponseEntity<EmpresaParceiraDTO> create(@Valid @RequestBody CreateEmpresaParceiraDTO createEmpresaParceiraDTO) {
        EmpresaParceiraDTO empresa = empresaParceiraService.create(createEmpresaParceiraDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(empresa);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(autenticacaoService.autenticar(loginRequest));
    }
}
