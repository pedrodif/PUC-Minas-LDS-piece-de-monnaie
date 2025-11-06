package com.lab.piece_de_monnaie.dto.auth;

public record LoginResponse (UsuarioResponse usuarioResponse,
                             JwtResponse jwtResponse) {}
