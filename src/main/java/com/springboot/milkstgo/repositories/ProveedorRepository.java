package com.springboot.milkstgo.repositories;

import com.springboot.milkstgo.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends CrudRepository<ProveedorEntity, Long> {

    @Query(value = "SELECT p.codigo FROM proveedores AS p",
            nativeQuery = true)
    List<String> findAllCodigo();

    @Query(value = "SELECT * FROM proveedores AS p WHERE p.codigo = :codigo",
            nativeQuery = true)
    ProveedorEntity findProveedorByCodigo(@Param("codigo") String codigo);

}
