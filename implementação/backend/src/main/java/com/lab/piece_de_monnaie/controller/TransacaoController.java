package com.lab.piece_de_monnaie.controller;

import com.lab.piece_de_monnaie.entity.Transacao;
import com.lab.piece_de_monnaie.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/transacoes")
@RequiredArgsConstructor()
public class TransacaoController {

    private final TransacaoService transacaoService;

    @GetMapping()
    public ResponseEntity<List<Transacao>> getAll() {
        return ResponseEntity.ok(transacaoService.findAll());
    }
}
