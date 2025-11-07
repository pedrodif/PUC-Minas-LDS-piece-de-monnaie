CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE instituicao_ensino (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    CNPJ VARCHAR(18) NOT NULL UNIQUE,
    email VARCHAR(255)
);

CREATE TABLE curso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    departamento VARCHAR(255) NOT NULL,
    instituicao_ensino_id BIGINT NOT NULL,
    FOREIGN KEY (instituicao_ensino_id) REFERENCES instituicao_ensino(id)
);

CREATE TABLE professor (
    id BIGINT PRIMARY KEY,
    CPF VARCHAR(14) NOT NULL UNIQUE,
    departamento VARCHAR(255),
    quantidade_moeda BIGINT DEFAULT 0,
    instituicao_ensino_id BIGINT NOT NULL,
    FOREIGN KEY (id) REFERENCES usuario(id),
    FOREIGN KEY (instituicao_ensino_id) REFERENCES instituicao_ensino(id)
);

CREATE TABLE aluno (
    id BIGINT PRIMARY KEY,
    CPF VARCHAR(14) NOT NULL UNIQUE,
    RG VARCHAR(12) UNIQUE,
    email VARCHAR(255) UNIQUE,
    endereco VARCHAR(255),
    quantidade_moeda BIGINT DEFAULT 0,
    curso_id BIGINT NOT NULL,
    FOREIGN KEY (id) REFERENCES usuario(id),
    FOREIGN KEY (curso_id) REFERENCES curso(id)
);

CREATE TABLE empresa_parceira (
    id BIGINT PRIMARY KEY,
    CNPJ VARCHAR(18) NOT NULL UNIQUE,
    email VARCHAR(255) UNIQUE,
    FOREIGN KEY (id) REFERENCES usuario(id)
);


CREATE TABLE vantagem (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    valor BIGINT NOT NULL,
    descricao VARCHAR(255),
    imagem LONGTEXT,
    empresa_parceira_id BIGINT NOT NULL,
    FOREIGN KEY (empresa_parceira_id) REFERENCES empresa_parceira(id)
);


CREATE TABLE transacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    montante BIGINT NOT NULL,
    feitaEm DATETIME DEFAULT CURRENT_TIMESTAMP,
    mensagem VARCHAR(255),
    tipo TINYINT NOT NULL,
    vantagem_id BIGINT,
    emissor_id BIGINT NOT NULL,
    receptor_id BIGINT NOT NULL,
    FOREIGN KEY (vantagem_id) REFERENCES vantagem(id),
    FOREIGN KEY (emissor_id) REFERENCES usuario(id),
    FOREIGN KEY (receptor_id) REFERENCES usuario(id)
);