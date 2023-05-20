package com.springboot.milkstgo.services;

import com.springboot.milkstgo.entities.ProveedorEntity;
import com.springboot.milkstgo.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;

    public void elininarProveedores(){
        proveedorRepository.deleteAll();
    }
    public List<ProveedorEntity> obtenerProveedores(){
        return (ArrayList<ProveedorEntity>) proveedorRepository.findAll();
    }
    public ProveedorEntity guardarProveedor(ProveedorEntity proveedor){
        return proveedorRepository.save(proveedor);
    }

    public List<String> obtenerCodigoProveedores(){
        return proveedorRepository.findAllCodigo();
    }

    public ProveedorEntity obtenerProovedorByCodigo(String codigo){return proveedorRepository.findProveedorByCodigo(codigo);}
}
