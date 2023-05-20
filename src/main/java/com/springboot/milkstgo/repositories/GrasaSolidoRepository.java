package com.springboot.milkstgo.repositories;

import com.springboot.milkstgo.entities.GrasaSolidoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GrasaSolidoRepository extends CrudRepository<GrasaSolidoEntity, Long > {

    @Query(value = "SELECT * FROM grasas_solidos AS g WHERE g.proveedor = :proveedor",
            nativeQuery = true)
    GrasaSolidoEntity findByProveedor(@Param("proveedor") String proveedor);
}
