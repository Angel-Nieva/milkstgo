package com.springboot.milkstgo.repositories;

import com.springboot.milkstgo.entities.AcopioEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AcopioRepository extends CrudRepository<AcopioEntity, Long> {
    @Query(value = "SELECT SUM(a.kls_leche) FROM acopios AS a where a.proveedor = :proveedor",
            nativeQuery = true)
    Integer  klsLecheByproveedor(@Param("proveedor") String proveedor);

    @Query(value = "SELECT COUNT(*) FROM acopios AS a where a.turno = 'M' and a.proveedor = :proveedor",
            nativeQuery = true)
    Integer  envioProveedorManana(@Param("proveedor") String proveedor);

    @Query(value = "SELECT COUNT(*) FROM acopios AS a where a.turno = 'T' and a.proveedor = :proveedor",
            nativeQuery = true)
    Integer  envioProveedorTarde(@Param("proveedor") String proveedor);

    @Query(value = "SELECT a.fecha FROM acopios AS a where a.proveedor = :proveedor ORDER BY fecha ASC limit 1",
            nativeQuery = true)
    Date quincenaByProveedor(@Param("proveedor") String proveedor);

    @Query(value = " SELECT COUNT(DISTINCT a.fecha) FROM acopios AS a where a.proveedor = :proveedor",
            nativeQuery = true)
    Integer diasEnvioLeche(@Param("proveedor") String proveedor);

    @Query(value = "SELECT ROUND(AVG(lecheDiaria)) FROM ( SELECT SUM(a.kls_leche) as lecheDiaria FROM acopios AS a where a.proveedor = :proveedor GROUP by a.fecha) AS T",
            nativeQuery = true)
    Integer avgEnvioLeche(@Param("proveedor") String proveedor);
}
