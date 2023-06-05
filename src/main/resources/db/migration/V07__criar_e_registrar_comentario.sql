create table `comentario`(
	 `id` INTEGER PRIMARY KEY auto_increment NOT NULL,
	 `texto` TEXT NOT NULL,
	 `data_comentario` DATETIME NOT NULL,
     `usuario_id` INTEGER NOT NULL,
     `post_id`    INTEGER NOT NULL,
     FOREIGN KEY (`usuario_id`) REFERENCES `usuario`(id),
     FOREIGN KEY (`post_id`)    REFERENCES `post`(id)
);