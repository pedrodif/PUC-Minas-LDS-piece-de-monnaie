START TRANSACTION;

INSERT INTO usuario (username, senha, nome, tipo)
VALUES ('lele', '$2a$10$.zc/lbWHhdVxj6QnKUkg0e8ewwq71HgOClb.jmCUQTZ1Q0K.8rEka', 'Lelê', 'PROFESSOR'); -- senha: pw

SET @id = LAST_INSERT_ID();

INSERT INTO professor (id, CPF, departamento, quantidade_moeda, instituicao_ensino_id)
VALUES (@id, '123.456.789-00', 'Theranos', 100, 1);

COMMIT;

