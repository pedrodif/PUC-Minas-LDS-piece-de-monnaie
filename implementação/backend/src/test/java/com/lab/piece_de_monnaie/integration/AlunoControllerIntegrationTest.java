package com.lab.piece_de_monnaie.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
public class AlunoControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // ========== TESTES PARA GET /api/alunos ==========

    @Test
    void deveListarTodosOsAlunos() throws Exception {
        mockMvc.perform(get("/api/alunos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].cpf", is("123.456.789-01")))
                .andExpect(jsonPath("$[0].email", is("elizabeth.holmes@aluno.pucminas.br")))
                .andExpect(jsonPath("$[0].cursoId", is(1)))
                .andExpect(jsonPath("$[0].cursoNome", is("Ciência da Computação")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].cpf", is("987.654.321-02")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[3].id", is(5)));
    }

    // ========== TESTES PARA GET /api/alunos/{id} ==========

    @Test
    void deveBuscarAlunoPorIdExistente() throws Exception {
        mockMvc.perform(get("/api/alunos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.cpf", is("123.456.789-01")))
                .andExpect(jsonPath("$.rg", is("12.345.678-9")))
                .andExpect(jsonPath("$.email", is("elizabeth.holmes@aluno.pucminas.br")))
                .andExpect(jsonPath("$.endereco", is("Rua das Flores, 123")))
                .andExpect(jsonPath("$.quantidadeMoeda", is(0)))
                .andExpect(jsonPath("$.cursoId", is(1)))
                .andExpect(jsonPath("$.cursoNome", is("Ciência da Computação")));
    }

    @Test
    void deveRetornar404ParaAlunoInexistente() throws Exception {
        mockMvc.perform(get("/api/alunos/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Aluno não encontrado com ID: 999")));
    }

    // ========== TESTES PARA POST /api/alunos ==========

    @Test
    void deveCriarNovoAlunoComDadosValidos() throws Exception {
        String novoAlunoJson = """
                {
                    "username": "novo.aluno",
                    "senha": "123456",
                    "nome": "Novo Aluno",
                    "cpf": "555.666.777-88",
                    "rg": "55.666.777-8",
                    "email": "novo.aluno@aluno.pucminas.br",
                    "endereco": "Rua Nova, 456",
                    "cursoId": 1
                }
                """;

        mockMvc.perform(post("/api/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(novoAlunoJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.cpf", is("555.666.777-88")))
                .andExpect(jsonPath("$.rg", is("55.666.777-8")))
                .andExpect(jsonPath("$.email", is("novo.aluno@aluno.pucminas.br")))
                .andExpect(jsonPath("$.endereco", is("Rua Nova, 456")))
                .andExpect(jsonPath("$.quantidadeMoeda", is(0)))
                .andExpect(jsonPath("$.cursoId", is(1)))
                .andExpect(jsonPath("$.cursoNome", is("Ciência da Computação")));
    }

    @Test
    void deveRetornar400ParaDadosInvalidos() throws Exception {
        String dadosInvalidosJson = """
                {
                    "username": "",
                    "senha": "",
                    "nome": "",
                    "cpf": "123",
                    "rg": "123",
                    "email": "email-invalido",
                    "endereco": "",
                    "cursoId": null
                }
                """;

        mockMvc.perform(post("/api/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosInvalidosJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username", is("Username é obrigatório")))
                .andExpect(jsonPath("$.senha", is("Senha é obrigatória")))
                .andExpect(jsonPath("$.nome", is("Nome é obrigatório")))
                .andExpect(jsonPath("$.cpf", is("CPF deve estar no formato XXX.XXX.XXX-XX")))
                .andExpect(jsonPath("$.rg", is("RG deve estar no formato XX.XXX.XXX-X")))
                .andExpect(jsonPath("$.email", is("Email deve ter um formato válido")))
                .andExpect(jsonPath("$.cursoId", is("Curso é obrigatório")));
    }

    @Test
    void deveRetornar400ParaCamposObrigatoriosFaltando() throws Exception {
        String dadosIncompletosJson = """
                {
                    "cpf": "555.666.777-88",
                    "rg": "55.666.777-8",
                    "email": "teste@aluno.pucminas.br",
                    "endereco": "Rua Teste, 123",
                    "cursoId": 1
                }
                """;

        mockMvc.perform(post("/api/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosIncompletosJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.senha", is("Senha é obrigatória")))
                .andExpect(jsonPath("$.nome", is("Nome é obrigatório")))
                .andExpect(jsonPath("$.username", is("Username é obrigatório")));
    }

    // ========== TESTES PARA PUT /api/alunos/{id} ==========

    @Test
    void deveAtualizarAlunoExistente() throws Exception {
        String dadosAtualizacaoJson = """
                {
                    "nome": "Aluno Atualizado",
                    "email": "atualizado@aluno.pucminas.br",
                    "endereco": "Endereço Atualizado, 789",
                    "cursoId": 1
                }
                """;

        mockMvc.perform(put("/api/alunos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAtualizacaoJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.cpf", is("123.456.789-01")))
                .andExpect(jsonPath("$.email", is("atualizado@aluno.pucminas.br")))
                .andExpect(jsonPath("$.endereco", is("Endereço Atualizado, 789")))
                .andExpect(jsonPath("$.cursoId", is(1)));
    }

    @Test
    void deveRetornar400ParaDadosInvalidosNaAtualizacao() throws Exception {
        String dadosInvalidosJson = """
                {
                    "email": "email-invalido",
                    "rg": "123"
                }
                """;

        mockMvc.perform(put("/api/alunos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosInvalidosJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rg", is("RG deve estar no formato XX.XXX.XXX-X")))
                .andExpect(jsonPath("$.email", is("Email deve ter um formato válido")));
    }

    @Test
    void deveRetornar404ParaAtualizacaoDeAlunoInexistente() throws Exception {
        String dadosAtualizacaoJson = """
                {
                    "nome": "Teste"
                }
                """;

        mockMvc.perform(put("/api/alunos/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAtualizacaoJson))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Aluno não encontrado com ID: 999")));
    }

    // ========== TESTES PARA DELETE /api/alunos/{id} ==========

    @Test
    void deveDeletarAlunoExistente() throws Exception {
        mockMvc.perform(delete("/api/alunos/5"))
                .andExpect(status().isNoContent());

        // Verificar se o aluno foi realmente deletado
        mockMvc.perform(get("/api/alunos/5"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("Aluno não encontrado com ID: 5")));
    }

    @Test
    void deveRetornar404ParaDelecaoDeAlunoInexistente() throws Exception {
        mockMvc.perform(delete("/api/alunos/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Aluno não encontrado com ID: 999")));
    }

    // ========== TESTES PARA GET /api/alunos/curso/{cursoId} ==========

    @Test
    void deveBuscarAlunosPorCursoExistente() throws Exception {
        mockMvc.perform(get("/api/alunos/curso/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].cursoId", is(1)))
                .andExpect(jsonPath("$[0].cursoNome", is("Ciência da Computação")))
                .andExpect(jsonPath("$[1].cursoId", is(1)))
                .andExpect(jsonPath("$[2].cursoId", is(1)))
                .andExpect(jsonPath("$[3].cursoId", is(1)));
    }

    @Test
    void deveRetornarListaVaziaParaCursoInexistente() throws Exception {
        mockMvc.perform(get("/api/alunos/curso/999"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
