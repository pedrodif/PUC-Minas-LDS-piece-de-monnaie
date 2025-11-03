package com.lab.piece_de_monnaie.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAlunoDTO {
    private String senha;
    
    private String nome;
    
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}-\\d{1}", message = "RG deve estar no formato XX.XXX.XXX-X")
    private String rg;
    
    @Email(message = "Email deve ter um formato válido")
    private String email;
    
    private String endereco;
    
    private Long cursoId;
}
