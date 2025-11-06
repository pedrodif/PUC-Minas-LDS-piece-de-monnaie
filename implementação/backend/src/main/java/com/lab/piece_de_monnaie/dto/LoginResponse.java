package com.lab.piece_de_monnaie.dto;

import com.lab.piece_de_monnaie.dto.user.UsuarioResponse;

public record LoginResponse (UsuarioResponse usuarioResponse,
                             JwtResponse jwtResponse) {}
