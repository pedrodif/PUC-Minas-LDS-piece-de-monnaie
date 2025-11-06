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
@Table(name = "vantagem")
public class Vantagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor")
    private Long valor;

    @Column(name = "descricao")
    private Long descricao;

    @Column(name = "imagem")
    private Long imagem;

    @ManyToOne
    @JoinColumn(name = "empresa_parceira_id")
    private EmpresaParceira empresaParceira;
}
