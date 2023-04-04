package com.springboot.milkstgo.repositories;

import com.springboot.milkstgo.entities.PlanillaPagoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;

public interface PlanillaPagoRepository extends CrudRepository<PlanillaPagoEntity, Long > {

    @Query(value = "DELETE FROM planilla_pagos as p WHERE p.quincena = :fecha",
            nativeQuery = true)
    void  deleteByFecha(@Param("fecha") Date fecha);

    @Query(value = "SELECT * FROM planilla_pagos as p WHERE p.codigo_proveedor = :codigo AND p.quincena < :fecha ORDER BY p.quincena DESC limit 1",
            nativeQuery = true)
    PlanillaPagoEntity findPlanillaAnterior(@Param("fecha") Date fecha, @Param("codigo") String codigo );

}
