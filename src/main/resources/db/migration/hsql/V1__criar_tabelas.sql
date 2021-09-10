CREATE SEQUENCE IF NOT EXISTS setor_id_seq AS BIGINT START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS setor (
  id BIGINT GENERATED BY DEFAULT AS SEQUENCE setor_id_seq PRIMARY KEY,
  descricao VARCHAR(80) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS colaborador_id_seq AS BIGINT START WITH 1 INCREMENT BY 1;
CREATE TABLE colaborador (
  id BIGINT GENERATED BY DEFAULT AS SEQUENCE colaborador_id_seq PRIMARY KEY,
  nome VARCHAR(80) NOT NULL,
  cpf VARCHAR(80) NOT NULL,
  setor_id BIGINT  NULL,
  idade  BIGINT  NULL,
  email VARCHAR(80)  NULL,
  telefone VARCHAR(80)  NULL,
  FOREIGN KEY (setor_id) REFERENCES setor(id)
);