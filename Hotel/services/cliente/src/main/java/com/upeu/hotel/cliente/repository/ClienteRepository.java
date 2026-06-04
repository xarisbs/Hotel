package com.upeu.hotel.cliente.repository;

import com.upeu.hotel.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByDocumento(String documento);

    long countByNacionalidad(String nacionalidad);

    @Query("""
            SELECT c FROM Cliente c
            WHERE LOWER(c.documento) LIKE LOWER(CONCAT('%', :q, '%'))
               OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :q, '%'))
               OR LOWER(c.apellido) LIKE LOWER(CONCAT('%', :q, '%'))
               OR LOWER(c.email) LIKE LOWER(CONCAT('%', :q, '%'))
            ORDER BY c.apellido, c.nombre
            """)
    List<Cliente> buscar(@Param("q") String q);
}
