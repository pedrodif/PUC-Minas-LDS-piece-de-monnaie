package com.lab.piece_de_monnaie.dto;

import com.lab.piece_de_monnaie.dto.auth.EmpresaParceiraResponse;

public record VantagemResponse(Long id,
                               Long valor,
                               String descricao,
                               String nomeImagem,
                               String imagem,
                               EmpresaParceiraResponse empresaParceira) {
}
