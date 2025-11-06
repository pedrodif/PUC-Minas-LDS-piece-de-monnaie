package com.lab.piece_de_monnaie.dto.user;

import com.lab.piece_de_monnaie.entity.InstituicaoDeEnsino;
import com.lab.piece_de_monnaie.type.TipoUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
public class ProfessorResponse extends UsuarioResponse {

    private final String cpf;
    private final String departamento;
    private final Long quantidadeMoeda;
    private final InstituicaoDeEnsino instituicaoDeEnsino;
    public ProfessorResponse(Long id,
                             String username,
                             String cpf,
                             String departamento,
                             Long quantidadeMoeda,
                             InstituicaoDeEnsino instituicaoDeEnsino) {
        super(id, username, TipoUsuario.PROFESSOR);
        this.cpf = cpf;
        this.departamento = departamento;
        this.quantidadeMoeda = quantidadeMoeda;
        this.instituicaoDeEnsino = instituicaoDeEnsino;
    }
}
