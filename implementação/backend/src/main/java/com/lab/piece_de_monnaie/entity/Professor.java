package com.lab.piece_de_monnaie.entity;

import com.lab.piece_de_monnaie.entity.interfaces.Poupançavel;
import com.lab.piece_de_monnaie.type.TipoUsuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "professor")
@PrimaryKeyJoinColumn(name = "id")
public class Professor extends Usuario implements Poupançavel {

    @Column(name = "CPF", unique = true, length = 14)
    private String cpf;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "quantidadeMoeda")
    private Long quantidadeMoeda;

    @ManyToOne
    @JoinColumn(name = "instituicao_ensino_id")
    private InstituicaoDeEnsino instituicaoDeEnsino;

    @PrePersist
    public void prePersist() {
        super.setTipo(TipoUsuario.PROFESSOR);
    }

    @Override
    public boolean possuiSaldoSuficiente(Long montante){
        return this.quantidadeMoeda >= montante;
    }
}

