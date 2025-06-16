package com.Perfulandia.ApiCupones.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Perfulandia.ApiCupones.dto.CuponDTO;
import com.Perfulandia.ApiCupones.services.CuponService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/cupones")
public class CuponController {

@Autowired
private CuponService cuponService;

@GetMapping
public ResponseEntity<List<CuponDTO>> listar() {
return ResponseEntity.ok(cuponService.listarCupones());
}

@GetMapping("/{id}")
public ResponseEntity<CuponDTO> obtenerPorId(@PathVariable Integer id) {
try {
return ResponseEntity.ok(cuponService.obtenerCuponPorId(id));
} catch (EntityNotFoundException e) {
return ResponseEntity.notFound().build();
}
}

@PostMapping
public ResponseEntity<CuponDTO> crear(@RequestBody CuponDTO dto) {
CuponDTO cuponCreado = cuponService.crearCupon(dto);
return ResponseEntity.status(HttpStatus.CREATED).body(cuponCreado);
}

@PutMapping("/{id}")
public ResponseEntity<CuponDTO> actualizar(@PathVariable Integer id, @RequestBody CuponDTO dto) {
try {
return ResponseEntity.ok(cuponService.actualizarCupon(id, dto));
} catch (EntityNotFoundException e) {
return ResponseEntity.notFound().build();
}
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
try {
cuponService.eliminarCupon(id);
return ResponseEntity.noContent().build();
} catch (EntityNotFoundException e) {
return ResponseEntity.notFound().build();
}
}


}