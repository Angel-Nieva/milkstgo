package com.springboot.milkstgo.services;

import com.springboot.milkstgo.entities.ProveedorEntity;
import com.springboot.milkstgo.repositories.ProveedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProveedorServiceTest {

    @Autowired
    ProveedorRepository proveedorRepository;
    @Autowired
    ProveedorService proveedorService;

    ProveedorEntity proveedor1;

    @BeforeEach
    void setUp() {
        proveedor1 = new ProveedorEntity();
        proveedor1.setCategoria("A");
        proveedor1.setCodigo("13005");
        proveedor1.setNombre("Alimentos Valle Central");
        proveedor1.setRetencion("Si");
    }

    @Test
    void obtenerProveedores() {
        //When
        List<ProveedorEntity> proveedoresDB = proveedorService.obtenerProveedores();
        //Then
        assertThat(proveedoresDB).isNotNull();
        assertThat(proveedoresDB.size()).isEqualTo(3);
    }

    @Test
    void guardarProveedor() {
        //Given
        proveedorService.guardarProveedor(proveedor1);
        //When
        ProveedorEntity proveedorDB = proveedorService.obtenerProovedorByCodigo(proveedor1.getCodigo());
        //Then
        assertThat(proveedorDB).isNotNull();
        assertThat(proveedorDB.getId()).isPositive();
        proveedorRepository.delete(proveedor1);
    }

    @Test
    void obtenerCodigoProveedores() {
        //Given
        proveedorService.guardarProveedor(proveedor1);
        //When
        List<String> codigos = proveedorService.obtenerCodigoProveedores();
        //Then
        assertThat(codigos).isNotNull();
        proveedorRepository.delete(proveedor1);
    }

    @Test
    void obtenerProovedorByCodigo() {
        //Given
        proveedorService.guardarProveedor(proveedor1);
        //When
        ProveedorEntity proveedorDB = proveedorService.obtenerProovedorByCodigo(proveedor1.getCodigo());
        //Then
        assertThat(proveedorDB).isNotNull();
        assertEquals("13005",proveedorDB.getCodigo());
        proveedorRepository.delete(proveedor1);
    }
}