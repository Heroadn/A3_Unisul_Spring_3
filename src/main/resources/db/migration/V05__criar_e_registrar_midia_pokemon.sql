create table midia_pokemon(
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	data_alteracao DATETIME NOT NULL,
	midia_id   INTEGER NOT NULL,
    pokemon_id INTEGER NOT NULL,
    FOREIGN KEY (midia_id) REFERENCES midia(id),
    FOREIGN KEY (pokemon_id) REFERENCES pokemon(id)
);