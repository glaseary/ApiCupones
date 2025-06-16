package com.Perfulandia.ApiCupones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Perfulandia.ApiCupones.models.Cupon;

@Repository
public interface CuponRepository extends JpaRepository<Cupon, Integer>{
}