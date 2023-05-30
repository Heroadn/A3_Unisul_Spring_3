create table `comentario`(
	 `id` INTEGER PRIMARY KEY auto_increment NOT NULL,
	 `texto` TEXT NOT NULL,
	 `data_comentario` DATETIME NOT NULL,
     `id_usuario` INTEGER NOT NULL,
     `id_post`    INTEGER NOT NULL,
     FOREIGN KEY (`id_usuario`) REFERENCES `usuario`(id),
     FOREIGN KEY (`id_post`)    REFERENCES `post`(id)
);