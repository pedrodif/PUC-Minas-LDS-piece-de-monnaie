package com.lab.piece_de_monnaie.dto;

import com.lab.piece_de_monnaie.type.TipoUsuario;

public record UsuarioResponse (Long id,
                               String username,
                               TipoUsuario tipo) {}
