package com.lab.piece_de_monnaie.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab.piece_de_monnaie.entity.EmpresaParceira;
import com.lab.piece_de_monnaie.repository.EmpresaParceiraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EmpresaParceiraControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmpresaParceiraRepository empresaParceiraRepository;

    @BeforeEach
    void setUp() {
        empresaParceiraRepository.deleteAll();
    }

    // ========== TESTES PARA GET /api/empresas-parceiras ==========

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverEmpresas() throws Exception {
        mockMvc.perform(get("/api/empresas-parceiras"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void deveRetornarListaDeEmpresasQuandoExistirem() throws Exception {
        // Criar empresa de teste
        EmpresaParceira empresa = new EmpresaParceira();
        empresa.setUsername("test.empresa");
        empresa.setSenha("123456");
        empresa.setNome("Empresa Teste");
        empresa.setCnpj("12.345.678/0001-90");
        empresa.setEmail("teste@empresa.com.br");
        empresaParceiraRepository.save(empresa);

        mockMvc.perform(get("/api/empresas-parceiras"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Empresa Teste")))
                .andExpect(jsonPath("$[0].cnpj", is("12.345.678/0001-90")))
                .andExpect(jsonPath("$[0].email", is("teste@empresa.com.br")));
    }

    // ========== TESTES PARA GET /api/empresas-parceiras/{id} ==========

    @Test
    void deveRetornarEmpresaQuandoIdExistir() throws Exception {
        // Criar empresa de teste
        EmpresaParceira empresa = new EmpresaParceira();
        empresa.setUsername("test.empresa");
        empresa.setSenha("123456");
        empresa.setNome("Empresa Teste");
        empresa.setCnpj("12.345.678/0001-90");
        empresa.setEmail("teste@empresa.com.br");
        EmpresaParceira savedEmpresa = empresaParceiraRepository.save(empresa);

        mockMvc.perform(get("/api/empresas-parceiras/" + savedEmpresa.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(savedEmpresa.getId().intValue())))
                .andExpect(jsonPath("$.nome", is("Empresa Teste")))
                .andExpect(jsonPath("$.cnpj", is("12.345.678/0001-90")))
                .andExpect(jsonPath("$.email", is("teste@empresa.com.br")));
    }

    @Test
    void deveRetornar404QuandoIdNaoExistir() throws Exception {
        mockMvc.perform(get("/api/empresas-parceiras/999"))
                .andExpect(status().isNotFound());
    }

    // ========== TESTES PARA POST /api/empresas-parceiras ==========

    @Test
    void deveCriarNovaEmpresaComDadosValidos() throws Exception {
        String novaEmpresaJson = """
                {
                    "username": "nova.empresa",
                    "senha": "123456",
                    "nome": "Nova Empresa",
                    "cnpj": "11.222.333/0001-44",
                    "email": "nova@empresa.com.br"
                }
                """;

        mockMvc.perform(post("/api/empresas-parceiras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(novaEmpresaJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nome", is("Nova Empresa")))
                .andExpect(jsonPath("$.cnpj", is("11.222.333/0001-44")))
                .andExpect(jsonPath("$.email", is("nova@empresa.com.br")));
    }

    @Test
    void deveRetornar400ParaDadosInvalidos() throws Exception {
        String empresaInvalidaJson = """
                {
                    "username": "",
                    "senha": "",
                    "nome": "",
                    "cnpj": "123",
                    "email": "email-invalido"
                }
                """;

        mockMvc.perform(post("/api/empresas-parceiras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaInvalidaJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar400ParaCNPJDuplicado() throws Exception {
        // Criar empresa com CNPJ
        EmpresaParceira empresaExistente = new EmpresaParceira();
        empresaExistente.setUsername("empresa.existente");
        empresaExistente.setSenha("123456");
        empresaExistente.setNome("Empresa Existente");
        empresaExistente.setCnpj("12.345.678/0001-90");
        empresaExistente.setEmail("existente@empresa.com.br");
        empresaParceiraRepository.save(empresaExistente);

        String empresaDuplicadaJson = """
                {
                    "username": "nova.empresa",
                    "senha": "123456",
                    "nome": "Nova Empresa",
                    "cnpj": "12.345.678/0001-90",
                    "email": "nova@empresa.com.br"
                }
                """;

        mockMvc.perform(post("/api/empresas-parceiras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaDuplicadaJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar400ParaEmailDuplicado() throws Exception {
        // Criar empresa com email
        EmpresaParceira empresaExistente = new EmpresaParceira();
        empresaExistente.setUsername("empresa.existente");
        empresaExistente.setSenha("123456");
        empresaExistente.setNome("Empresa Existente");
        empresaExistente.setCnpj("12.345.678/0001-90");
        empresaExistente.setEmail("existente@empresa.com.br");
        empresaParceiraRepository.save(empresaExistente);

        String empresaDuplicadaJson = """
                {
                    "username": "nova.empresa",
                    "senha": "123456",
                    "nome": "Nova Empresa",
                    "cnpj": "11.222.333/0001-44",
                    "email": "existente@empresa.com.br"
                }
                """;

        mockMvc.perform(post("/api/empresas-parceiras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaDuplicadaJson))
                .andExpect(status().isBadRequest());
    }

    // ========== TESTES PARA PUT /api/empresas-parceiras/{id} ==========

    @Test
    void deveAtualizarEmpresaExistente() throws Exception {
        // Criar empresa de teste
        EmpresaParceira empresa = new EmpresaParceira();
        empresa.setUsername("test.empresa");
        empresa.setSenha("123456");
        empresa.setNome("Empresa Teste");
        empresa.setCnpj("12.345.678/0001-90");
        empresa.setEmail("teste@empresa.com.br");
        EmpresaParceira savedEmpresa = empresaParceiraRepository.save(empresa);

        String empresaAtualizadaJson = """
                {
                    "nome": "Empresa Atualizada",
                    "email": "atualizada@empresa.com.br"
                }
                """;

        mockMvc.perform(put("/api/empresas-parceiras/" + savedEmpresa.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaAtualizadaJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome", is("Empresa Atualizada")))
                .andExpect(jsonPath("$.email", is("atualizada@empresa.com.br")))
                .andExpect(jsonPath("$.cnpj", is("12.345.678/0001-90")));
    }

    @Test
    void deveRetornar404AoAtualizarEmpresaInexistente() throws Exception {
        String empresaAtualizadaJson = """
                {
                    "nome": "Empresa Atualizada"
                }
                """;

        mockMvc.perform(put("/api/empresas-parceiras/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empresaAtualizadaJson))
                .andExpect(status().isNotFound());
    }

    // ========== TESTES PARA DELETE /api/empresas-parceiras/{id} ==========

    @Test
    void deveDeletarEmpresaExistente() throws Exception {
        // Criar empresa de teste
        EmpresaParceira empresa = new EmpresaParceira();
        empresa.setUsername("test.empresa");
        empresa.setSenha("123456");
        empresa.setNome("Empresa Teste");
        empresa.setCnpj("12.345.678/0001-90");
        empresa.setEmail("teste@empresa.com.br");
        EmpresaParceira savedEmpresa = empresaParceiraRepository.save(empresa);

        mockMvc.perform(delete("/api/empresas-parceiras/" + savedEmpresa.getId()))
                .andExpect(status().isNoContent());

        // Verificar se foi deletada
        mockMvc.perform(get("/api/empresas-parceiras/" + savedEmpresa.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar404AoDeletarEmpresaInexistente() throws Exception {
        mockMvc.perform(delete("/api/empresas-parceiras/999"))
                .andExpect(status().isNotFound());
    }
}
