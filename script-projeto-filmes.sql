CREATE DATABASE projetofilmes;
SHOW databases;
USE projetofilmes;

CREATE TABLE genero (
  generoid INTEGER PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(200) NOT NULL
);

CREATE TABLE usuario (
  usuarioid INTEGER  PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(200) NOT NULL
);

CREATE TABLE filme (
  filmeid INTEGER  PRIMARY KEY AUTO_INCREMENT,
  titulo VARCHAR(250) NOT NULL,  
  data_lancamento DATE NOT NULL,
  nome_diretor VARCHAR(255) NOT NULL,
  generoid INTEGER NOT NULL
);

CREATE TABLE avaliacao (
  avaliacaoid INTEGER  PRIMARY KEY AUTO_INCREMENT,
  usuarioid INTEGER NOT NULL,
  filmeid INTEGER NOT NULL,
  nota INTEGER NOT NULL
);

ALTER TABLE filme ADD FOREIGN KEY (generoid) REFERENCES genero (generoid);
ALTER TABLE avaliacao ADD FOREIGN KEY (usuarioid) REFERENCES usuario (usuarioid);
ALTER TABLE avaliacao ADD FOREIGN KEY (filmeid) REFERENCES filme (filmeid);

SET@@global.time_zone='-03:00';

SELECT * FROM genero;
SELECT * FROM avaliacao;


