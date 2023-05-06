create table midia_usuario(
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	data_alteracao DATETIME NOT NULL,
	midia_id   INTEGER NOT NULL,
    usuario_id INTEGER NOT NULL,
    FOREIGN KEY (midia_id) REFERENCES midia(id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);