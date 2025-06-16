package com.Perfulandia.ApiCupones.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "CUPON")
@Data
public class Cupon {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id_cupon")
private Integer idCupon;

@Column(name = "nombre_cupon", nullable = false)
private String nombreCupon;

}
