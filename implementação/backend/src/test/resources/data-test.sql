-- Inserir curso para os testes
INSERT INTO curso (id, nome) VALUES (1, 'Ciência da Computação');

-- Inserir usuários para os testes
INSERT INTO usuario (id, username, senha, nome) VALUES 
(1, 'elizabeth.holmes', 'senha123', 'Elizabeth Holmes'),
(2, 'godines', 'senha123', 'Godines'),
(3, 'rolando.lero', 'senha123', 'Rolando Lero'),
(4, 'teste', 'senha123', 'Usuário Teste');

-- Inserir alunos para os testes
INSERT INTO aluno (id, cpf, rg, email, endereco, quantidade_moeda, curso_id, usuario_id) VALUES 
(1, '123.456.789-01', '12.345.678-9', 'elizabeth.holmes@aluno.pucminas.br', 'Rua das Flores, 123', 0, 1, 1),
(2, '987.654.321-02', '98.765.432-1', 'godines@aluno.pucminas.br', 'Avenida Central, 456', 0, 1, 2),
(3, '456.789.123-03', '45.678.912-3', 'rolando.lero@aluno.pucminas.br', 'Praça da Liberdade, 789', 0, 1, 3),
(5, '111.222.333-44', '11.222.333-4', 'teste@aluno.pucminas.br', 'Rua Teste, 123', 0, 1, 4);
