START TRANSACTION;

INSERT INTO usuario (username, senha, nome, tipo)
VALUES ('chell', '$2a$10$.zc/lbWHhdVxj6QnKUkg0e8ewwq71HgOClb.jmCUQTZ1Q0K.8rEka', 'chell', 'ALUNO'); -- senha: pw

SET @id = LAST_INSERT_ID();

INSERT INTO aluno (id, CPF, RG, email, endereco, quantidade_moeda, curso_id)
VALUES (@id, 'XXX.XXX.XXX-XX', 'XX.XXX.XXX-X', 'chell@test.com', 'aperture lab', 10, 1);

COMMIT;

START TRANSACTION;

INSERT INTO usuario (username, senha, nome, tipo)
VALUES ('glad0s', '$2a$10$.zc/lbWHhdVxj6QnKUkg0e8ewwq71HgOClb.jmCUQTZ1Q0K.8rEka', 'glag0s', 'EMPRESA'); -- senha: pw

SET @id = LAST_INSERT_ID();

INSERT INTO empresa_parceira (id, CNPJ, email)
VALUES (@id, 'XX.XXX.XXX/XXXX-XX', 'glados@aperture.com');

COMMIT;