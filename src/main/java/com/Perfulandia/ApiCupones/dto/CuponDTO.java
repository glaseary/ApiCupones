package com.Perfulandia.ApiCupones.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class CuponDTO extends RepresentationModel<CuponDTO> {
private Integer idCupon;
private String nombreCupon;
}