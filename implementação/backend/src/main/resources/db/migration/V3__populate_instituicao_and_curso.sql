INSERT INTO instituicao_ensino
(nome,
cnpj,
email)
VALUE("nome", "cnpj", "email");

INSERT INTO curso
(nome,
 departamento,
 instituicao_ensino_id)
 VALUE ("nome", "departamento", 1);