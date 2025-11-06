package com.lab.piece_de_monnaie.dto.user;

import com.lab.piece_de_monnaie.entity.Curso;
import com.lab.piece_de_monnaie.type.TipoUsuario;
import lombok.*;

@Getter
@Setter
public class AlunoResponse extends UsuarioResponse {

    private final String cpf;
    private final String rg;
    private final String email;
    private final String endereco;
    private final Long quantidadeMoeda;
    private final Curso curso;
    public AlunoResponse(Long id,
                  String username,
                  String cpf,
                  String rg,
                  String email,
                  String endereco,
                  Long quantidadeMoeda,
                  Curso curso) {
        super(id, username, TipoUsuario.ALUNO);
        this.cpf = cpf;
        this.rg = rg;
        this.email = email;
        this.endereco = endereco;
        this.quantidadeMoeda = quantidadeMoeda;
        this.curso = curso;
    }
}


