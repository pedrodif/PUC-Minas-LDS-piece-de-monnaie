package com.lab.piece_de_monnaie.dto.auth;

import com.lab.piece_de_monnaie.type.TipoUsuario;
import lombok.Getter;

@Getter
public class EmpresaParceiraResponse extends UsuarioResponse {

    private final String cnpj;
    private final String email;
    public EmpresaParceiraResponse(Long id,
                                   String username,
                                   String cnpj,
                                   String email) {
        super(id, username, TipoUsuario.EMPRESA);
        this.cnpj = cnpj;
        this.email = email;
    }
}
