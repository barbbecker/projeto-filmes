CREATE DATABASE projetofilmes;
SHOW databases;
USE projetofilmes;

CREATE TABLE genero (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(200) NOT NULL
);

CREATE TABLE usuario (
  id INTEGER  PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(200) NOT NULL UNIQUE
);

CREATE TABLE filme (
  id INTEGER  PRIMARY KEY AUTO_INCREMENT,
  titulo VARCHAR(250) NOT NULL,  
  data_lancamento DATE NOT NULL,
  nome_diretor VARCHAR(255) NOT NULL,
  genero_id INTEGER NOT NULL
);

CREATE TABLE avaliacao (
  id INTEGER  PRIMARY KEY AUTO_INCREMENT,
  usuario_id INTEGER NOT NULL,
  filme_id INTEGER NOT NULL,
  nota INTEGER NOT NULL
);

ALTER TABLE filme ADD FOREIGN KEY (genero_id) REFERENCES genero (id);
ALTER TABLE avaliacao ADD FOREIGN KEY (usuario_id) REFERENCES usuario (id);
ALTER TABLE avaliacao ADD FOREIGN KEY (filme_id) REFERENCES filme (id);

INSERT INTO genero (nome) VALUES 
('Ação'),
('Aventura'),
('Comédia'),
('Documentário'),
('Faroeste'),
('Ficção Científica'),
('Romance'),
('Suspense'),
('Tragédia');

SET@@global.time_zone='-03:00';