-- Atualizar departamento do professor Lelê para "Tecnologia da Informação"
UPDATE professor 
SET departamento = 'Tecnologia da Informação' 
WHERE id = (SELECT id FROM usuario WHERE username = 'lele');

-- Adicionar aluno "Meu Filho"
START TRANSACTION;

INSERT INTO usuario (username, senha, nome, tipo)
VALUES ('meu.filho', '$2a$10$.zc/lbWHhdVxj6QnKUkg0e8ewwq71HgOClb.jmCUQTZ1Q0K.8rEka', 'Meu Filho', 'ALUNO'); -- senha: pw

SET @id = LAST_INSERT_ID();

INSERT INTO aluno (id, CPF, RG, email, endereco, quantidade_moeda, curso_id)
VALUES (@id, '999.888.777-66', '99.888.777-6', 'meu.filho@aluno.pucminas.br', 'Rua dos Pais, 123', 0, 1);

COMMIT;

