package com.lab.piece_de_monnaie.config;

import com.lab.piece_de_monnaie.entity.*;
import com.lab.piece_de_monnaie.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;
    private final InstituicaoDeEnsinoRepository instituicaoDeEnsinoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== INICIANDO DATA SEEDER ===");
        
        // Verificar se já existem dados para evitar duplicação
        if (alunoRepository.count() > 0) {
            System.out.println("Dados já existem no banco. Pulando seed.");
            return;
        }
        
        System.out.println("Banco vazio. Iniciando seed...");

        // Verificar se a instituição já existe
        InstituicaoDeEnsino pucMinas = instituicaoDeEnsinoRepository.findByCnpj("12.345.678/0001-90")
                .orElseGet(() -> {
                    System.out.println("Criando instituição de ensino...");
                    InstituicaoDeEnsino novaInstituicao = InstituicaoDeEnsino.builder()
                            .nome("Pontifícia Universidade Católica de Minas Gerais")
                            .cnpj("12.345.678/0001-90")
                            .email("contato@pucminas.br")
                            .build();
                    return instituicaoDeEnsinoRepository.save(novaInstituicao);
                });

        // Verificar se o curso já existe
        Curso cienciaComputacao = cursoRepository.findByNomeAndInstituicaoDeEnsino("Ciência da Computação", pucMinas)
                .orElseGet(() -> {
                    System.out.println("Criando curso...");
                    Curso novoCurso = Curso.builder()
                            .nome("Ciência da Computação")
                            .departamento("1") // ID do departamento como string
                            .instituicaoDeEnsino(pucMinas)
                            .build();
                    return cursoRepository.save(novoCurso);
                });

        // Criar usuários e alunos
        System.out.println("Criando alunos...");
        createAluno("elizabeth.holmes", "Elizabeth Holmes", "123.456.789-01", "12.345.678-9", 
                   "elizabeth.holmes@aluno.pucminas.br", "Rua das Flores, 123", cienciaComputacao);
        
        createAluno("godines", "Godines Silva", "987.654.321-02", "98.765.432-1", 
                   "godines@aluno.pucminas.br", "Avenida Central, 456", cienciaComputacao);
        
        createAluno("rolando.lero", "Rolando Lero", "456.789.123-03", "45.678.912-3", 
                   "rolando.lero@aluno.pucminas.br", "Praça da Liberdade, 789", cienciaComputacao);
        
        System.out.println("Seed concluído com sucesso!");
    }

           private void createAluno(String username, String nome, String cpf, String rg, 
                                  String email, String endereco, Curso curso) {
               // Verificar se o aluno já existe
               if (alunoRepository.existsByCpf(cpf)) {
                   System.out.println("Aluno " + nome + " já existe. Pulando...");
                   return;
               }
               
               // Criar aluno (que herda de Usuario)
               Aluno aluno = new Aluno();
               aluno.setUsername(username);
               aluno.setSenha("123456");
               aluno.setNome(nome);
               aluno.setCpf(cpf);
               aluno.setRg(rg);
               aluno.setEmail(email);
               aluno.setEndereco(endereco);
               aluno.setQuantidadeMoeda(0L);
               aluno.setCurso(curso);
               alunoRepository.save(aluno);
               System.out.println("Aluno " + nome + " criado com sucesso!");
           }
}
