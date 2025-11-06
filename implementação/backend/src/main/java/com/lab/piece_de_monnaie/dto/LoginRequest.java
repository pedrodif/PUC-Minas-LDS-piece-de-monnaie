package com.lab.piece_de_monnaie.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Informe seu nome de usuário para login.")
        String username,
        @NotBlank(message = "Informe sua senha para login.")
        String senha) {}
