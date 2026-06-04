package com.upeu.hotel.tipohabitacion.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upeu.hotel.tipohabitacion.dto.TipoHabitacionRequest;
import com.upeu.hotel.tipohabitacion.dto.TipoHabitacionResponse;
import com.upeu.hotel.tipohabitacion.entity.TipoHabitacion;
import com.upeu.hotel.tipohabitacion.exception.ResourceNotFoundException;
import com.upeu.hotel.tipohabitacion.mapper.TipoHabitacionMapper;
import com.upeu.hotel.tipohabitacion.repository.TipoHabitacionRepository;
import com.upeu.hotel.tipohabitacion.service.TipoHabitacionService;

import java.util.List;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
@Slf4j
public class TipoHabitacionServiceImpl implements TipoHabitacionService {

    private final TipoHabitacionRepository tipoHabitacionRepository;
    private final TipoHabitacionMapper tipoHabitacionMapper;

    // =========================
    // CREAR CATEGORIA
    // =========================
    @Override
    @Transactional
    public TipoHabitacionResponse create(TipoHabitacionRequest request) {

        try {

            log.info("Iniciando creación de categoría con nombre: {}",
                    request.getNombre());

            String nombreImagen = null;

            // Verifica si viene imagen
            if (request.getImagen() != null
                    && !request.getImagen().isEmpty()) {

                // Obtiene el nombre original
                nombreImagen = request.getImagen().getOriginalFilename();

                // Ruta donde se guardará la imagen
                Path ruta = Paths.get("uploads/tipos-habitacion");

                // Si no existe carpeta, la crea
                if (!Files.exists(ruta)) {
                    Files.createDirectories(ruta);
                }

                // Guarda la imagen
                Files.copy(
                        request.getImagen().getInputStream(),
                        ruta.resolve(nombreImagen),
                        StandardCopyOption.REPLACE_EXISTING);
            }

            // Convierte request a entidad
            TipoHabitacion categoria = tipoHabitacionMapper.toEntity(request);

            // Guarda nombre de imagen
            categoria.setImagen(nombreImagen);

            // Guarda en BD
            TipoHabitacion savedTipoHabitacion = tipoHabitacionRepository.save(categoria);

            log.info("Categoría creada exitosamente con ID: {}",
                    savedTipoHabitacion.getId());

            return tipoHabitacionMapper.toResponse(savedTipoHabitacion);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error al guardar imagen: "
                            + e.getMessage());
        }
    }

    // =========================
    // LISTAR TODAS
    // =========================
    @Override
    @Transactional(readOnly = true)
    public List<TipoHabitacionResponse> findAll() {

        log.info("Recuperando lista de categorías");

        List<TipoHabitacionResponse> categorias = tipoHabitacionRepository.findAll()
                .stream()
                .map(tipoHabitacionMapper::toResponse)
                .toList();

        log.info("Se encontraron {} categorías",
                categorias.size());

        return categorias;
    }

    // =========================
    // BUSCAR POR ID
    // =========================
    @Override
    @Transactional(readOnly = true)
    public TipoHabitacionResponse findById(Long id) {

        log.info("Buscando categoría con ID: {}", id);

        TipoHabitacion categoria = getTipoHabitacionById(id);

        return tipoHabitacionMapper.toResponse(categoria);
    }

    // =========================
    // ACTUALIZAR
    // =========================
    @Override
    @Transactional
    public TipoHabitacionResponse update(Long id,
            TipoHabitacionRequest request) {

        try {

            log.info("Iniciando actualización de categoría ID: {}",
                    id);

            TipoHabitacion categoria = getTipoHabitacionById(id);

            // Actualiza nombre
            categoria.setNombre(request.getNombre());
            categoria.setDescripcion(request.getDescripcion());
            if (request.getCapacidadMaxima() != null) {
                categoria.setCapacidadMaxima(request.getCapacidadMaxima());
            }

            // Verifica si viene imagen
            if (request.getImagen() != null
                    && !request.getImagen().isEmpty()) {

                // Obtiene nombre original
                String nombreImagen = request.getImagen().getOriginalFilename();

                // Ruta
                Path ruta = Paths.get("uploads/tipos-habitacion");

                // Crea carpeta si no existe
                if (!Files.exists(ruta)) {
                    Files.createDirectories(ruta);
                }

                // Guarda imagen
                Files.copy(
                        request.getImagen().getInputStream(),
                        ruta.resolve(nombreImagen),
                        StandardCopyOption.REPLACE_EXISTING);

                // Guarda nombre en BD
                categoria.setImagen(nombreImagen);
            }

            TipoHabitacion updatedTipoHabitacion = tipoHabitacionRepository.save(categoria);

            log.info("Categoría ID: {} actualizada exitosamente",
                    id);

            return tipoHabitacionMapper.toResponse(updatedTipoHabitacion);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error al actualizar categoría: "
                            + e.getMessage());
        }
    }

    // =========================
    // ELIMINAR
    // =========================
    @Override
    @Transactional
    public void delete(Long id) {

        log.info("Iniciando eliminación de categoría ID: {}",
                id);

        TipoHabitacion categoria = getTipoHabitacionById(id);

        // Elimina imagen física si existe
        if (categoria.getImagen() != null) {

            try {

                Path ruta = Paths.get(
                        "uploads/tipos-habitacion",
                        categoria.getImagen());

                Files.deleteIfExists(ruta);

            } catch (Exception e) {

                log.error("Error eliminando imagen: {}",
                        e.getMessage());
            }
        }

        // Elimina de MySQL
        tipoHabitacionRepository.deleteById(id);

        log.info("Categoría ID: {} eliminada exitosamente",
                id);
    }

    // =========================
    // BUSCAR CATEGORIA
    // =========================
    private TipoHabitacion getTipoHabitacionById(Long id) {

        return tipoHabitacionRepository.findById(id)
                .orElseThrow(() -> {

                    log.warn(
                            "Categoría no encontrada: ID {}",
                            id);

                    return new ResourceNotFoundException(
                            "Categoría con id "
                                    + id
                                    + " no encontrada");
                });
    }
}