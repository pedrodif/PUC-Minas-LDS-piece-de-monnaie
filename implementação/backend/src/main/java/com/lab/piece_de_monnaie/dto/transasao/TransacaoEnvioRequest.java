package com.lab.piece_de_monnaie.dto.transasao;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TransacaoEnvioRequest(
        @Min(0) Long montante,
        @NotNull String mensagem) {
}
