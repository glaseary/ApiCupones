package com.Perfulandia.ApiCupones.services;

import com.Perfulandia.ApiCupones.dto.CuponDTO;
import com.Perfulandia.ApiCupones.models.Cupon;
import com.Perfulandia.ApiCupones.repository.CuponRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CuponServiceTest {

    @Mock
    private CuponRepository cuponRepository;

    @InjectMocks
    private CuponService cuponService;

    private Cupon cupon;
    private CuponDTO cuponDTO;

    @BeforeEach
    void setUp() {
        cupon = new Cupon();
        cupon.setIdCupon(1);
        cupon.setNombreCupon("DESCUENTO2025");

        cuponDTO = new CuponDTO();
        cuponDTO.setIdCupon(1);
        cuponDTO.setNombreCupon("DESCUENTO2025");
    }

    @Test
    @DisplayName("Debería listar todos los cupones")
    void testListarCupones() {
        // Arrange
        when(cuponRepository.findAll()).thenReturn(List.of(cupon));

        // Act
        List<CuponDTO> resultados = cuponService.listarCupones();

        // Assert
        assertThat(resultados).isNotNull().hasSize(1);
        assertThat(resultados.get(0).getNombreCupon()).isEqualTo("DESCUENTO2025");
    }

    @Test
    @DisplayName("Debería obtener un cupón por su ID")
    void testObtenerCuponPorIdExitoso() {
        // Arrange
        when(cuponRepository.findById(1)).thenReturn(Optional.of(cupon));

        // Act
        CuponDTO resultado = cuponService.obtenerCuponPorId(1);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdCupon()).isEqualTo(1);
    }

    @Test
    @DisplayName("Debería lanzar excepción si el cupón no se encuentra por ID")
    void testObtenerCuponPorIdNoEncontrado() {
        // Arrange
        when(cuponRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> cuponService.obtenerCuponPorId(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Cupón no encontrado con ID: 99");
    }

    @Test
    @DisplayName("Debería crear un nuevo cupón")
    void testCrearCupon() {
        // Arrange
        when(cuponRepository.save(any(Cupon.class))).thenReturn(cupon);

        // Act
        CuponDTO resultado = cuponService.crearCupon(cuponDTO);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombreCupon()).isEqualTo("DESCUENTO2025");
        verify(cuponRepository, times(1)).save(any(Cupon.class));
    }

    @Test
    @DisplayName("Debería actualizar un cupón existente")
    void testActualizarCupon() {
        // Arrange
        CuponDTO dtoActualizado = new CuponDTO();
        dtoActualizado.setNombreCupon("NUEVOCODIGO");

        when(cuponRepository.findById(1)).thenReturn(Optional.of(cupon));
        when(cuponRepository.save(any(Cupon.class))).thenReturn(cupon);

        // Act
        CuponDTO resultado = cuponService.actualizarCupon(1, dtoActualizado);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombreCupon()).isEqualTo("NUEVOCODIGO");
        verify(cuponRepository, times(1)).save(cupon);
    }

    @Test
    @DisplayName("Debería eliminar un cupón existente")
    void testEliminarCupon() {
        // Arrange
        when(cuponRepository.existsById(1)).thenReturn(true);
        doNothing().when(cuponRepository).deleteById(1);

        // Act
        cuponService.eliminarCupon(1);

        // Assert
        verify(cuponRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Debería lanzar excepción al intentar eliminar un cupón que no existe")
    void testEliminarCuponNoEncontrado() {
        // Arrange
        when(cuponRepository.existsById(99)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> cuponService.eliminarCupon(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("No se puede eliminar. Cupón no encontrado con ID: 99");
    }
}