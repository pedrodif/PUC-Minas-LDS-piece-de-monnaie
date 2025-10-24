package com.lab.piece_de_monnaie.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAlunoDTO {
    @NotBlank(message = "Username é obrigatório")
    private String username;
    
    @NotBlank(message = "Senha é obrigatória")
    private String senha;
    
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato XXX.XXX.XXX-XX")
    private String cpf;
    
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}-\\d{1}", message = "RG deve estar no formato XX.XXX.XXX-X")
    private String rg;
    
    @Email(message = "Email deve ter um formato válido")
    private String email;
    
    private String endereco;
    
    @NotNull(message = "Curso é obrigatório")
    private Long cursoId;
}
