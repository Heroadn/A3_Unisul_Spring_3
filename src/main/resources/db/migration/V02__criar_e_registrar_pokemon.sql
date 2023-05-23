create table `pokemon`(
    `id`     INTEGER PRIMARY KEY AUTO_INCREMENT,
    `nome`   VARCHAR(50)  NOT NULL,
    `ataque` INTEGER NOT NULL,
    `defesa` INTEGER NOT NULL,
    `tipo`   VARCHAR(100)
);
