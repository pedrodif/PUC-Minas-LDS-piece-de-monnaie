package com.lab.piece_de_monnaie.dto.auth;

import com.lab.piece_de_monnaie.entity.InstituicaoDeEnsino;
import com.lab.piece_de_monnaie.type.TipoUsuario;
import lombok.Getter;

@Getter
public class ProfessorResponse extends UsuarioResponse {

    private final String cpf;
    private final String departamento;
    private final Long quantidadeMoeda;
    private final InstituicaoDeEnsino instituicaoDeEnsino;
    public ProfessorResponse(Long id,
                             String username,
                             String nome,
                             String cpf,
                             String departamento,
                             Long quantidadeMoeda,
                             InstituicaoDeEnsino instituicaoDeEnsino) {
        super(id, username, nome, TipoUsuario.PROFESSOR);
        this.cpf = cpf;
        this.departamento = departamento;
        this.quantidadeMoeda = quantidadeMoeda;
        this.instituicaoDeEnsino = instituicaoDeEnsino;
    }
}
