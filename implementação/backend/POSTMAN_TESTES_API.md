# Guia de Testes da API - Piece de Monnaie

## Configuração Inicial

### 1. Configuração do Postman
- **Base URL**: `http://localhost:8080`
- **Content-Type**: `application/json` (para requisições POST e PUT)

### 2. Iniciar a Aplicação
Antes de executar os testes, certifique-se de que a aplicação está rodando:
```bash
cd /home/pedro/PUC/disciplinas/laboratorio-projeto/PUC-Minas-LDS-piece-de-monnaie/implementação/backend
./mvnw spring-boot:run
```

---

## 📋 Lista de Testes

### 1. **GET /api/alunos** - Listar Todos os Alunos

**Método**: `GET`  
**URL**: `http://localhost:8080/api/alunos`  
**Headers**: Nenhum  
**Body**: Nenhum  

**Resultado Esperado**:
- Status: `200 OK`
- Response: Array com 4 alunos
- Verificar se contém os alunos com IDs: 1, 2, 3, 5

**Exemplo de Response**:
```json
[
  {
    "id": 1,
    "username": "elizabeth.holmes",
    "senha": "senha123",
    "nome": "Elizabeth Holmes",
    "cpf": "123.456.789-01",
    "rg": "12.345.678-9",
    "email": "elizabeth.holmes@aluno.pucminas.br",
    "endereco": "Rua das Flores, 123",
    "quantidadeMoeda": 0,
    "cursoId": 1,
    "cursoNome": "Ciência da Computação"
  }
  // ... outros alunos
]
```

---

### 2. **GET /api/alunos/{id}** - Buscar Aluno por ID

#### 2.1. Buscar Aluno Existente

**Método**: `GET`  
**URL**: `http://localhost:8080/api/alunos/1`  
**Headers**: Nenhum  
**Body**: Nenhum  

**Resultado Esperado**:
- Status: `200 OK`
- Response: Objeto do aluno com ID 1

#### 2.2. Buscar Aluno Inexistente

**Método**: `GET`  
**URL**: `http://localhost:8080/api/alunos/999`  
**Headers**: Nenhum  
**Body**: Nenhum  

**Resultado Esperado**:
- Status: `404 Not Found`
- Response: `{"error": "Aluno não encontrado com ID: 999"}`

---

### 3. **POST /api/alunos** - Criar Novo Aluno

#### 3.1. Criar Aluno com Dados Válidos

**Método**: `POST`  
**URL**: `http://localhost:8080/api/alunos`  
**Headers**: 
```
Content-Type: application/json
```
**Body** (raw JSON):
```json
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
```

**Resultado Esperado**:
- Status: `201 Created`
- Response: Objeto do aluno criado com ID gerado
- Verificar se `quantidadeMoeda` é 0
- Verificar se `cursoNome` é "Ciência da Computação"

#### 3.2. Criar Aluno com Dados Inválidos

**Método**: `POST`  
**URL**: `http://localhost:8080/api/alunos`  
**Headers**: 
```
Content-Type: application/json
```
**Body** (raw JSON):
```json
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
```

**Resultado Esperado**:
- Status: `400 Bad Request`
- Response: Objeto com mensagens de erro para cada campo inválido

#### 3.3. Criar Aluno com Campos Obrigatórios Faltando

**Método**: `POST`  
**URL**: `http://localhost:8080/api/alunos`  
**Headers**: 
```
Content-Type: application/json
```
**Body** (raw JSON):
```json
{
  "cpf": "555.666.777-88",
  "rg": "55.666.777-8",
  "email": "teste@aluno.pucminas.br",
  "endereco": "Rua Teste, 123",
  "cursoId": 1
}
```

**Resultado Esperado**:
- Status: `400 Bad Request`
- Response: Objeto com mensagens de erro para campos obrigatórios faltando

---

### 4. **PUT /api/alunos/{id}** - Atualizar Aluno

#### 4.1. Atualizar Aluno Existente

**Método**: `PUT`  
**URL**: `http://localhost:8080/api/alunos/1`  
**Headers**: 
```
Content-Type: application/json
```
**Body** (raw JSON):
```json
{
  "nome": "Aluno Atualizado",
  "email": "atualizado@aluno.pucminas.br",
  "endereco": "Endereço Atualizado, 789",
  "cursoId": 1
}
```

**Resultado Esperado**:
- Status: `200 OK`
- Response: Objeto do aluno atualizado
- Verificar se os campos foram atualizados corretamente

#### 4.2. Atualizar Aluno com Dados Inválidos

**Método**: `PUT`  
**URL**: `http://localhost:8080/api/alunos/1`  
**Headers**: 
```
Content-Type: application/json
```
**Body** (raw JSON):
```json
{
  "email": "email-invalido",
  "rg": "123"
}
```

**Resultado Esperado**:
- Status: `400 Bad Request`
- Response: Objeto com mensagens de erro para campos inválidos

#### 4.3. Atualizar Aluno Inexistente

**Método**: `PUT`  
**URL**: `http://localhost:8080/api/alunos/999`  
**Headers**: 
```
Content-Type: application/json
```
**Body** (raw JSON):
```json
{
  "nome": "Teste"
}
```

**Resultado Esperado**:
- Status: `404 Not Found`
- Response: `{"error": "Aluno não encontrado com ID: 999"}`

---

### 5. **DELETE /api/alunos/{id}** - Deletar Aluno

#### 5.1. Deletar Aluno Existente

**Método**: `DELETE`  
**URL**: `http://localhost:8080/api/alunos/5`  
**Headers**: Nenhum  
**Body**: Nenhum  

**Resultado Esperado**:
- Status: `204 No Content`
- Response: Vazio

**Verificação Adicional**:
Após deletar, tentar buscar o aluno deletado:
- **Método**: `GET`
- **URL**: `http://localhost:8080/api/alunos/5`
- **Resultado Esperado**: `404 Not Found`

#### 5.2. Deletar Aluno Inexistente

**Método**: `DELETE`  
**URL**: `http://localhost:8080/api/alunos/999`  
**Headers**: Nenhum  
**Body**: Nenhum  

**Resultado Esperado**:
- Status: `404 Not Found`
- Response: `{"error": "Aluno não encontrado com ID: 999"}`

---

### 6. **GET /api/alunos/curso/{cursoId}** - Buscar Alunos por Curso

#### 6.1. Buscar Alunos por Curso Existente

**Método**: `GET`  
**URL**: `http://localhost:8080/api/alunos/curso/1`  
**Headers**: Nenhum  
**Body**: Nenhum  

**Resultado Esperado**:
- Status: `200 OK`
- Response: Array com 4 alunos do curso ID 1
- Verificar se todos os alunos têm `cursoId: 1`

#### 6.2. Buscar Alunos por Curso Inexistente

**Método**: `GET`  
**URL**: `http://localhost:8080/api/alunos/curso/999`  
**Headers**: Nenhum  
**Body**: Nenhum  

**Resultado Esperado**:
- Status: `200 OK`
- Response: Array vazio `[]`

---

## 🏢 **API Empresa Parceira**

### 1. **GET /api/empresas-parceiras** - Listar Todas as Empresas

#### 1.1. Listar Empresas (Lista Vazia)

**Método**: `GET`  
**URL**: `http://localhost:8080/api/empresas-parceiras`  
**Headers**: Nenhum

**Resultado Esperado**:
- Status: `200 OK`
- Response: Array vazio `[]`

#### 1.2. Listar Empresas (Com Dados)

**Método**: `GET`  
**URL**: `http://localhost:8080/api/empresas-parceiras`  
**Headers**: Nenhum

**Resultado Esperado**:
- Status: `200 OK`
- Response: Array com empresas parceiras cadastradas

---

### 2. **GET /api/empresas-parceiras/{id}** - Buscar Empresa por ID

#### 2.1. Buscar Empresa Existente

**Método**: `GET`  
**URL**: `http://localhost:8080/api/empresas-parceiras/1`  
**Headers**: Nenhum

**Resultado Esperado**:
- Status: `200 OK`
- Response: Objeto da empresa com todos os dados

#### 2.2. Buscar Empresa Inexistente

**Método**: `GET`  
**URL**: `http://localhost:8080/api/empresas-parceiras/999`  
**Headers**: Nenhum

**Resultado Esperado**:
- Status: `404 Not Found`

---

### 3. **POST /api/empresas-parceiras** - Criar Nova Empresa

#### 3.1. Criar Empresa com Dados Válidos

**Método**: `POST`  
**URL**: `http://localhost:8080/api/empresas-parceiras`  
**Headers**: 
```
Content-Type: application/json
```
**Body** (raw JSON):
```json
{
  "username": "nova.empresa",
  "senha": "123456",
  "nome": "Nova Empresa LTDA",
  "cnpj": "11.222.333/0001-44",
  "email": "contato@novaempresa.com.br"
}
```

**Resultado Esperado**:
- Status: `201 Created`
- Response: Objeto da empresa criada com ID gerado

#### 3.2. Criar Empresa com Dados Inválidos

**Método**: `POST`  
**URL**: `http://localhost:8080/api/empresas-parceiras`  
**Headers**: 
```
Content-Type: application/json
```
**Body** (raw JSON):
```json
{
  "username": "",
  "senha": "",
  "nome": "",
  "cnpj": "123",
  "email": "email-invalido"
}
```

**Resultado Esperado**:
- Status: `400 Bad Request`
- Response: Objeto com mensagens de erro para cada campo inválido

#### 3.3. Criar Empresa com CNPJ Duplicado

**Método**: `POST`  
**URL**: `http://localhost:8080/api/empresas-parceiras`  
**Headers**: 
```
Content-Type: application/json
```
**Body** (raw JSON):
```json
{
  "username": "empresa.duplicada",
  "senha": "123456",
  "nome": "Empresa Duplicada",
  "cnpj": "11.222.333/0001-44",
  "email": "duplicada@empresa.com.br"
}
```

**Resultado Esperado**:
- Status: `400 Bad Request`
- Response: Mensagem de erro indicando CNPJ já cadastrado

---

### 4. **PUT /api/empresas-parceiras/{id}** - Atualizar Empresa

#### 4.1. Atualizar Empresa Existente

**Método**: `PUT`  
**URL**: `http://localhost:8080/api/empresas-parceiras/1`  
**Headers**: 
```
Content-Type: application/json
```
**Body** (raw JSON):
```json
{
  "nome": "Empresa Atualizada LTDA",
  "email": "atualizada@empresa.com.br"
}
```

**Resultado Esperado**:
- Status: `200 OK`
- Response: Objeto da empresa atualizada
- Verificar se os campos foram atualizados corretamente

#### 4.2. Atualizar Empresa Inexistente

**Método**: `PUT`  
**URL**: `http://localhost:8080/api/empresas-parceiras/999`  
**Headers**: 
```
Content-Type: application/json
```
**Body** (raw JSON):
```json
{
  "nome": "Empresa Atualizada"
}
```

**Resultado Esperado**:
- Status: `404 Not Found`

---

### 5. **DELETE /api/empresas-parceiras/{id}** - Deletar Empresa

#### 5.1. Deletar Empresa Existente

**Método**: `DELETE`  
**URL**: `http://localhost:8080/api/empresas-parceiras/1`  
**Headers**: Nenhum

**Resultado Esperado**:
- Status: `204 No Content`
- Response: Vazio

#### 5.2. Deletar Empresa Inexistente

**Método**: `DELETE`  
**URL**: `http://localhost:8080/api/empresas-parceiras/999`  
**Headers**: Nenhum

**Resultado Esperado**:
- Status: `404 Not Found`

---

## 🔍 Validações Importantes

### Formato de Dados
- **CPF**: Deve estar no formato `XXX.XXX.XXX-XX`
- **RG**: Deve estar no formato `XX.XXX.XXX-X`
- **CNPJ**: Deve estar no formato `XX.XXX.XXX/XXXX-XX`
- **Email**: Deve ter formato válido de email
- **Campos Obrigatórios**: username, senha, nome, cpf, cursoId (para Aluno)
- **Campos Obrigatórios**: username, senha, nome, cnpj (para Empresa Parceira)

### Códigos de Status HTTP
- `200 OK`: Operação bem-sucedida
- `201 Created`: Recurso criado com sucesso
- `204 No Content`: Recurso deletado com sucesso
- `400 Bad Request`: Dados inválidos ou campos obrigatórios faltando
- `404 Not Found`: Recurso não encontrado

### Dados de Teste Iniciais
A aplicação inicia com os seguintes dados:
- **Curso**: ID 1 - "Ciência da Computação"
- **Alunos**: 4 alunos (IDs: 1, 2, 3, 5) todos do curso 1

---

## 📝 Checklist de Testes

- [ ] GET /api/alunos - Listar todos os alunos
- [ ] GET /api/alunos/1 - Buscar aluno existente
- [ ] GET /api/alunos/999 - Buscar aluno inexistente (404)
- [ ] POST /api/alunos - Criar aluno com dados válidos
- [ ] POST /api/alunos - Criar aluno com dados inválidos (400)
- [ ] POST /api/alunos - Criar aluno com campos faltando (400)
- [ ] PUT /api/alunos/1 - Atualizar aluno existente
- [ ] PUT /api/alunos/1 - Atualizar com dados inválidos (400)
- [ ] PUT /api/alunos/999 - Atualizar aluno inexistente (404)
- [ ] DELETE /api/alunos/5 - Deletar aluno existente
- [ ] GET /api/alunos/5 - Verificar se aluno foi deletado (404)
- [ ] DELETE /api/alunos/999 - Deletar aluno inexistente (404)
- [ ] GET /api/alunos/curso/1 - Buscar alunos por curso existente
- [ ] GET /api/alunos/curso/999 - Buscar alunos por curso inexistente

---

## 🚀 Dicas para Execução

1. **Execute os testes em ordem** para evitar conflitos
2. **Use o Collection Runner** do Postman para executar todos os testes automaticamente
3. **Verifique os logs da aplicação** para debug se necessário
4. **Mantenha a aplicação rodando** durante todos os testes
5. **Use variáveis do Postman** para facilitar a manutenção das URLs

---

## 📊 Relatório de Testes

Após executar todos os testes, documente:
- Quantos testes passaram
- Quantos testes falharam
- Quais foram os erros encontrados
- Tempo de execução dos testes
- Observações gerais sobre a API
