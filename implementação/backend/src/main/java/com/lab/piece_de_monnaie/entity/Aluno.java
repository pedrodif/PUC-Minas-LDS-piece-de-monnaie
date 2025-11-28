package com.lab.piece_de_monnaie.entity;

import com.lab.piece_de_monnaie.entity.interfaces.Poupançavel;
import com.lab.piece_de_monnaie.type.TipoUsuario;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "aluno")
@PrimaryKeyJoinColumn(name = "id")
public class Aluno extends Usuario implements Poupançavel {
    
    @Column(name = "cpf", unique = true, length = 14)
    private String cpf;
    
    @Column(name = "RG", unique = true, length = 12)
    private String rg;
    
    @Column(name = "email", unique = true)
    private String email;
    
    @Column(name = "endereco")
    private String endereco;
    
    @Column(name = "quantidade_moeda")
    private Long quantidadeMoeda;
    
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @PrePersist
    public void prePersist() {
        super.setTipo(TipoUsuario.ALUNO);
    }

    @Override
    public boolean possuiSaldoSuficiente(Long montante) {
        return this.getQuantidadeMoeda() >= montante;
    }

    @Override
    public void descontarMontante(Long montante) {
        this.quantidadeMoeda = this.quantidadeMoeda - montante;
    }
}
