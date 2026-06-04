CREATE TABLE habitaciones (
    id INT NOT NULL AUTO_INCREMENT,
    numero VARCHAR(10) NOT NULL,
    descripcion VARCHAR(255),
    id_tipo_habitacion INT NOT NULL,
    precio_por_noche DECIMAL(10,2) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'DISPONIBLE',
    PRIMARY KEY (id),
    UNIQUE KEY uk_habitacion_numero (numero)
);
