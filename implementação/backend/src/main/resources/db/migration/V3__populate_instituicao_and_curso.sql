INSERT INTO instituicao_ensino (nome, CNPJ, email) VALUES
('Pontifícia Universidade Católica de Minas Gerais', '17.217.150/0001-10', 'contato@pucminas.br'),
('Universidade de São Paulo', '63.025.550/0001-64', 'institucional@usp.br'),
('Universidade Federal de Minas Gerais', '17.217.187/0001-99', 'secretaria@ufmg.br'),
('Universidade Federal de Lavras', '00.414.275/0001-00', 'institucional@ufla.br'),
('Instituto Federal do Sul de Minas', '10.682.378/0001-22', 'contato@ifsmg.edu.br'),
('Universidade Presbiteriana Mackenzie', '60.922.104/0001-50', 'contato@mackenzie.br');

INSERT INTO curso (nome, departamento, instituicao_ensino_id) VALUES
('Sistemas de Informação', 'Tecnologia da Informação', 1),
('Engenharia de Software', 'Escola Politécnica', 2),
('Ciência da Computação', 'Exatas e Computação', 3),
('Administração', 'Ciências Sociais Aplicadas', 4),
('Engenharia Mecânica', 'Engenharias', 5),
('Psicologia', 'Ciências Humanas', 6);
