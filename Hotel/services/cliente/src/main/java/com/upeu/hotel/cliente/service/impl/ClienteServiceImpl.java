package com.upeu.hotel.cliente.service.impl;

import com.upeu.hotel.cliente.client.ReservaClient;
import com.upeu.hotel.cliente.dto.ClienteRequest;
import com.upeu.hotel.cliente.dto.ClienteResponse;
import com.upeu.hotel.cliente.dto.ReservaHistorialDTO;
import com.upeu.hotel.cliente.entity.Cliente;
import com.upeu.hotel.cliente.repository.ClienteRepository;
import com.upeu.hotel.cliente.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ReservaClient reservaClient;

    @Override
    @Transactional
    public ClienteResponse create(ClienteRequest request) {
        if (clienteRepository.findByDocumento(request.getDocumento()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un huésped con ese DNI");
        }
        return toResponse(clienteRepository.save(toEntity(request)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponse> findAll() {
        return clienteRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponse> buscar(String q) {
        if (q == null || q.isBlank()) {
            return findAll();
        }
        return clienteRepository.buscar(q.trim()).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse findById(Long id) {
        return toResponse(getCliente(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaHistorialDTO> historialReservas(Long id) {
        getCliente(id);
        return reservaClient.historialPorHuesped(id);
    }

    @Override
    @Transactional
    public ClienteResponse update(Long id, ClienteRequest request) {
        Cliente cliente = getCliente(id);
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setDocumento(request.getDocumento());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setNacionalidad(request.getNacionalidad());
        return toResponse(clienteRepository.save(cliente));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        getCliente(id);
        clienteRepository.deleteById(id);
    }

    private Cliente getCliente(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Huésped no encontrado: " + id));
    }

    private Cliente toEntity(ClienteRequest request) {
        return Cliente.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .documento(request.getDocumento())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .nacionalidad(request.getNacionalidad())
                .build();
    }

    private ClienteResponse toResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .documento(cliente.getDocumento())
                .dni(cliente.getDocumento())
                .email(cliente.getEmail())
                .correo(cliente.getEmail())
                .telefono(cliente.getTelefono())
                .nacionalidad(cliente.getNacionalidad())
                .nombreCompleto(cliente.getNombre() + " " + cliente.getApellido())
                .fechaRegistro(cliente.getFechaRegistro())
                .build();
    }
}
