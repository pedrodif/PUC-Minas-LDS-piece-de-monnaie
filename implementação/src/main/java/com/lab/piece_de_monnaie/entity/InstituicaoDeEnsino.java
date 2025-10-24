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
@Table(name = "instituicao_ensino")
public class InstituicaoDeEnsino {
    @Column(name = "CNPJ", unique = true, length = 18)
    private String cpnj;
    @Column(name = "email", unique = true)
    private String email;
}
