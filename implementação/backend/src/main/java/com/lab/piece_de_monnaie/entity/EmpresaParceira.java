package com.lab.piece_de_monnaie.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "empresa_parceira")
@PrimaryKeyJoinColumn(name = "id")
public class EmpresaParceira extends Usuario {
    
    @Column(name = "CNPJ", unique = true, length = 18)
    private String cnpj;
    
    @Column(name = "email", unique = true)
    private String email;
}
