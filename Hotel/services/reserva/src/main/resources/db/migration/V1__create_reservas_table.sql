CREATE TABLE reservas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_huesped BIGINT NULL,
    nombre_huesped VARCHAR(120) NOT NULL,
    id_habitacion BIGINT NOT NULL,
    numero_habitacion VARCHAR(10) NOT NULL,
    fecha_check_in DATE NOT NULL,
    fecha_check_out DATE NOT NULL,
    noches INT NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    PRIMARY KEY (id)
);
