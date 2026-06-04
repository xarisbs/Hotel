package com.upeu.hotel.facturacion.service.impl;

import com.upeu.hotel.facturacion.dto.PagoDTO;
import com.upeu.hotel.facturacion.entity.EstadoPago;
import com.upeu.hotel.facturacion.entity.MetodoPago;
import com.upeu.hotel.facturacion.entity.Pago;
import com.upeu.hotel.facturacion.mapper.PagoMapper;
import com.upeu.hotel.facturacion.repository.PagoRepository;
import com.upeu.hotel.facturacion.service.PagoService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {

    private final PagoRepository repository;

    public PagoServiceImpl(PagoRepository repository) {
        this.repository = repository;
    }

    @Override
    public PagoDTO crear(PagoDTO dto) {
        validar(dto);
        Pago pago = PagoMapper.toEntity(dto);
        Pago guardado = repository.save(pago);
        return PagoMapper.toDTO(guardado);
    }

    @Override
    public List<PagoDTO> listar() {
        return repository.findAll().stream().map(PagoMapper::toDTO).toList();
    }

    @Override
    public PagoDTO obtenerPorId(Long id) {
        Pago pago = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado"));
        return PagoMapper.toDTO(pago);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    private void validar(PagoDTO dto) {
        if (dto.getReservaId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La reserva es obligatoria");
        }
        if (dto.getMonto() == null || dto.getMonto().doubleValue() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Monto inválido");
        }
        if (dto.getMetodoPago() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Método de pago obligatorio");
        }
        if (!Arrays.asList(MetodoPago.values()).contains(dto.getMetodoPago())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Método de pago inválido");
        }
        if (dto.getEstado() != null && !Arrays.asList(EstadoPago.values()).contains(dto.getEstado())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado de pago inválido");
        }
    }
}
