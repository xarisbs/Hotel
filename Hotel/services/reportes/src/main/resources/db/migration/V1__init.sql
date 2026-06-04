CREATE TABLE reportes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    total_ingresos DECIMAL(12,2) NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    descripcion VARCHAR(500),
    fecha DATETIME NOT NULL,
    PRIMARY KEY (id)
);
