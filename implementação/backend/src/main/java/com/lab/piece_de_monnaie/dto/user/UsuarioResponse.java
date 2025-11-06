package com.lab.piece_de_monnaie.dto.user;

import com.lab.piece_de_monnaie.type.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioResponse {

    Long id;
    String username;
    TipoUsuario tipo;
}
