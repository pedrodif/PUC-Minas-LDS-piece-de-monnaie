package com.lab.piece_de_monnaie.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "professor")
@PrimaryKeyJoinColumn(name = "id")
public class Professor extends Usuario {
    @Column(name = "CPF", unique = true, length = 14)
    private String cpf;
    @Column(name = "departamento")
    private String departamento;
    @Column(name = "quantidadeMoeda")
    private String quantidadeMoeda;
    @ManyToOne
    @JoinColumn(name = "instituicao_ensino_id")
    private InstituicaoDeEnsino instituicaoDeEnsino;
}

