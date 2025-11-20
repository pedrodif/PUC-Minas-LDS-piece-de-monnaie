package com.lab.piece_de_monnaie.entity;

import com.lab.piece_de_monnaie.type.TipoTransacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "montante")
    private Long montante;

    @Column(name = "feitaEm")
    private LocalDateTime feitaEm;

    @Column(name = "mensagem")
    private String mensagem;

    @Column(name = "tipo")
    @Enumerated(EnumType.ORDINAL)
    private TipoTransacao tipo;

    @ManyToOne
    @JoinColumn(name = "vantagem_id")
    private Vantagem vantagem;

    @ManyToOne
    @JoinColumn(name = "emissor_id", nullable = false)
    private Usuario emissor;

    @ManyToOne
    @JoinColumn(name = "receptor_id", nullable = false)
    private Usuario receptor;

    @PrePersist
    public void prePersist() {
        this.feitaEm = LocalDateTime.now();
    }
}
