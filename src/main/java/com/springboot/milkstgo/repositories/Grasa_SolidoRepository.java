package com.springboot.milkstgo.repositories;

import com.springboot.milkstgo.entities.Grasa_SolidoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Grasa_SolidoRepository extends CrudRepository<Grasa_SolidoEntity, Long > {

    Grasa_SolidoEntity findByProveedor(String proveedor);
}
