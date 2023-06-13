create table `pokemon_atributo`(
    `id`	        INTEGER PRIMARY KEY AUTO_INCREMENT,
	`sexo`      	VARCHAR(255)  NOT NULL,
	`tipo`      	VARCHAR(255)  NOT NULL,
	`geracao`      	VARCHAR(50)  NOT NULL,
    `ataque_minimo`    INTEGER NOT NULL,
	`ataque_maximo`    INTEGER NOT NULL,
    `defesa_minimo`    INTEGER NOT NULL,
	`defesa_maximo`    INTEGER NOT NULL
);

create table `pokemon`(
    `id`            INTEGER PRIMARY KEY AUTO_INCREMENT,
    `nome`          VARCHAR(50)  NOT NULL,
    `descricao`     VARCHAR(255) ,
    `data_criacao`  DATE NOT NULL,
    `atributo_id`	INTEGER  NOT NULL,
     FOREIGN KEY (atributo_id) REFERENCES pokemon_atributo(id)
);
