package com.Perfulandia.ApiCupones.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Perfulandia.ApiCupones.dto.CuponDTO;
import com.Perfulandia.ApiCupones.models.Cupon;
import com.Perfulandia.ApiCupones.repository.CuponRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuponService {

    private final CuponRepository repository;

    @Autowired
    public CuponService(CuponRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CuponDTO crearCupon(CuponDTO dto) {
        Cupon cupon = toEntity(dto);
        Cupon saved = repository.save(cupon);
        return toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<CuponDTO> listarCupones() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CuponDTO obtenerCuponPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Cupón no encontrado con ID: " + id));
    }

    @Transactional
    public CuponDTO actualizarCupon(Integer id, CuponDTO dto) {
        return repository.findById(id)
                .map(cuponExistente -> {
                    Cupon cuponActualizado = toEntity(dto);
                    cuponActualizado.setIdCupon(id); // Asegurar que se mantenga el mismo ID
                    return toDTO(repository.save(cuponActualizado));
                })
                .orElseThrow(() -> new EntityNotFoundException("Cupón no encontrado con ID: " + id));
    }

    @Transactional
    public void eliminarCupon(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Cupón no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

    // --- Métodos auxiliares mejorados ---

    private CuponDTO toDTO(Cupon cupon) {
        CuponDTO dto = new CuponDTO();
        dto.setIdCupon(cupon.getIdCupon());
        dto.setNombreCupon(cupon.getNombreCupon());
        // Agregar todos los campos necesarios del cupón
        return dto;
    }

    private Cupon toEntity(CuponDTO dto) {
        Cupon cupon = new Cupon();
        cupon.setIdCupon(dto.getIdCupon());
        cupon.setNombreCupon(dto.getNombreCupon());
        // Mapear todos los campos necesarios
        return cupon;
    }
}