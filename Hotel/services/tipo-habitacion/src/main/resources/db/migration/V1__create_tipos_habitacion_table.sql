CREATE TABLE tipos_habitacion (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    imagen VARCHAR(255),
    capacidad_maxima INT NOT NULL DEFAULT 2,
    PRIMARY KEY (id)
);
