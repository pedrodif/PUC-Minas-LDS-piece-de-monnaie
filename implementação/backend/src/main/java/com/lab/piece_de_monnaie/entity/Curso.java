package com.lab.piece_de_monnaie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "departamento")
    private String departamento;

    @ManyToOne
    @JoinColumn(name = "instituicao_ensino_id")
    private InstituicaoDeEnsino instituicaoDeEnsino;
}
