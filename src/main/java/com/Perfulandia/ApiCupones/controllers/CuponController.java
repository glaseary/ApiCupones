package com.Perfulandia.ApiCupones.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Perfulandia.ApiCupones.dto.CuponDTO;
import com.Perfulandia.ApiCupones.services.CuponService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cupones")
public class CuponController {

    private final CuponService cuponService;

    @Autowired
    public CuponController(CuponService cuponService) {
        this.cuponService = cuponService;
    }

    @GetMapping
    public ResponseEntity<List<CuponDTO>> listarTodos() {
        return ResponseEntity.ok(cuponService.listarCupones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuponDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(cuponService.obtenerCuponPorId(id));
    }

    @PostMapping
    public ResponseEntity<CuponDTO> crear(@Valid @RequestBody CuponDTO dto) {
        CuponDTO cuponCreado = cuponService.crearCupon(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cuponCreado.getIdCupon())
                .toUri();
        return ResponseEntity.created(location).body(cuponCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuponDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody CuponDTO dto) {
        return ResponseEntity.ok(cuponService.actualizarCupon(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        cuponService.eliminarCupon(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}