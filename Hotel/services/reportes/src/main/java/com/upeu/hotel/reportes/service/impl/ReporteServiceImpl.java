package com.upeu.hotel.reportes.service.impl;

import com.upeu.hotel.reportes.dto.ReporteDTO;
import com.upeu.hotel.reportes.entity.Reporte;
import com.upeu.hotel.reportes.exception.RecursoNoEncontradoException;
import com.upeu.hotel.reportes.mapper.ReporteMapper;
import com.upeu.hotel.reportes.repository.ReporteRepository;
import com.upeu.hotel.reportes.service.ReporteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository repo;

    public ReporteServiceImpl(ReporteRepository repo) {
        this.repo = repo;
    }

    @Override
    public ReporteDTO crear(ReporteDTO dto) {
        Reporte r = ReporteMapper.toEntity(dto);
        return ReporteMapper.toDTO(repo.save(r));
    }

    @Override
    public List<ReporteDTO> listar() {
        return repo.findAll()
                .stream()
                .map(ReporteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReporteDTO obtenerPorId(Long id) {
        Reporte r = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
        return ReporteMapper.toDTO(r);
    }

    @Override

    public ReporteDTO actualizar(Long id, ReporteDTO dto) {

        Reporte r = repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reporte no encontrado con id: " + id));

        r.setTotalIngresos(dto.getTotalIngresos());
        r.setTipo(dto.getTipo());
        r.setDescripcion(dto.getDescripcion());
        r.setFecha(dto.getFecha());

        return ReporteMapper.toDTO(repo.save(r));
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}