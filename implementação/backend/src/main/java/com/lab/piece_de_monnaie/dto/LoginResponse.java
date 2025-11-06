package com.lab.piece_de_monnaie.dto;

public record LoginResponse (UsuarioResponse usuarioResponse,
                             JwtResponse jwtResponse) {}
