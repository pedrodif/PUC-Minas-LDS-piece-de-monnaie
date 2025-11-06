package com.lab.piece_de_monnaie.dto;

import jakarta.validation.constraints.NotBlank;

public record VantagemRequest(
        @NotBlank
        Long valor,
        @NotBlank
        String descricao,
        @NotBlank
        String imagem) {
}
