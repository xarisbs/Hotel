CREATE TABLE clientes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    documento VARCHAR(20) NOT NULL,
    email VARCHAR(150),
    telefono VARCHAR(20),
    nacionalidad VARCHAR(60),
    PRIMARY KEY (id),
    UNIQUE KEY uk_cliente_documento (documento)
);
