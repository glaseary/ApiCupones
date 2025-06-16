package com.Perfulandia.ApiCupones.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Perfulandia.ApiCupones.dto.CuponDTO;
import com.Perfulandia.ApiCupones.models.Cupon;
import com.Perfulandia.ApiCupones.repository.CuponRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuponService {

    @Autowired
    private CuponRepository cuponRepository;

    public List<CuponDTO> listarCupones() {
    return cuponRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public CuponDTO obtenerCuponPorId(Integer id) {
    return cuponRepository.findById(id).map(this::toDTO)
    .orElseThrow(() -> new EntityNotFoundException("Cupón no encontrado con ID: " + id));
    }

    public CuponDTO crearCupon(CuponDTO dto) {
    Cupon cupon = new Cupon();
    cupon.setNombreCupon(dto.getNombreCupon());
    return toDTO(cuponRepository.save(cupon));
    }

    public CuponDTO actualizarCupon(Integer id, CuponDTO dto) {
    Cupon cuponExistente = cuponRepository.findById(id)
    .orElseThrow(() -> new EntityNotFoundException("Cupón no encontrado con ID: " + id));

    cuponExistente.setNombreCupon(dto.getNombreCupon());
    return toDTO(cuponRepository.save(cuponExistente));
    }

    public void eliminarCupon(Integer id) {
    if (!cuponRepository.existsById(id)) {
    throw new EntityNotFoundException("No se puede eliminar. Cupón no encontrado con ID: " + id);
    }
    cuponRepository.deleteById(id);
    }

    private CuponDTO toDTO(Cupon cupon) {
    CuponDTO dto = new CuponDTO();
    dto.setIdCupon(cupon.getIdCupon());
    dto.setNombreCupon(cupon.getNombreCupon());
    return dto;
    }

}