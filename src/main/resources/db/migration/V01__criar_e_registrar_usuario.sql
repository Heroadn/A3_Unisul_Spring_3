create table `usuario`(
    `id`  		   INTEGER PRIMARY KEY AUTO_INCREMENT,
    `nome`         VARCHAR(50)  NOT NULL,
    `email`        CHAR(80)     NOT NULL,
    `descricao`    TEXT,
    `token`        VARCHAR(100),
    `data_criacao` DATE NOT NULL
);
