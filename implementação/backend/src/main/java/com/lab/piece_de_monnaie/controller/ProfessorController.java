package com.lab.piece_de_monnaie.controller;

import com.lab.piece_de_monnaie.dto.auth.AlunoResponse;
import com.lab.piece_de_monnaie.dto.transasao.TransacaoEnvioRequest;
import com.lab.piece_de_monnaie.entity.Transacao;
import com.lab.piece_de_monnaie.service.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/professores")
@RequiredArgsConstructor
public class ProfessorController {

    private final TransacaoService transacaoService;

    @PostMapping("/enviar-moedas/{alunoId}")
    public ResponseEntity<Transacao> sendMoedas(Authentication authentication,
                                                @PathVariable Long alunoId,
                                                @RequestBody @Valid TransacaoEnvioRequest transacaoEnvioRequest) {
        var transacao = transacaoService.solicitaTransacaoEnvio(authentication, alunoId, transacaoEnvioRequest);
        return ResponseEntity.ok(transacao);
    }
}
