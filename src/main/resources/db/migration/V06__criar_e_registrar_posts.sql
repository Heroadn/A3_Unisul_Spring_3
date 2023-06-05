create table post(
    `id` INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `titulo` VARCHAR(80),
    `texto` TEXT,
    `data_post` DATETIME NOT NULL,
    `usuario_id` INTEGER,
    FOREIGN KEY (`usuario_id`) references `usuario`(`id`)
);
