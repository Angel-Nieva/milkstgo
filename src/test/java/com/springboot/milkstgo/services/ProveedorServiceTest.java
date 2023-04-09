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

    ProveedorEntity proveedor1, proveedor2;

    @BeforeEach
    void setUp() {
        proveedorService.elininarProveedores();
        proveedor1 = new ProveedorEntity();
        proveedor1.setCategoria("A");
        proveedor1.setCodigo("13001");
        proveedor1.setNombre("Alimentos Valle Central");
        proveedor1.setRetencion("No");

        proveedor2 = new ProveedorEntity();
        proveedor2.setCategoria("B");
        proveedor2.setCodigo("10001");
        proveedor2.setNombre("Chilolac");
        proveedor2.setRetencion("Si");
    }

    @Test
    void obtenerProveedores() {
        //Given
        proveedorService.guardarProveedor(proveedor1);
        proveedorService.guardarProveedor(proveedor2);
        //When
        List<ProveedorEntity> proveedoresDB = proveedorService.obtenerProveedores();
        //Then
        assertThat(proveedoresDB).isNotNull();
        assertThat(proveedoresDB.size()).isEqualTo(2);
        proveedorService.elininarProveedores();
    }

    @Test
    void guardarProveedor() {
        //Given
        proveedorService.guardarProveedor(proveedor1);
        //When
        ProveedorEntity proveedorDB = proveedorService.obtenerProovedorByCodigo(proveedor1.getCodigo());
        //Then
        assertThat(proveedorDB).isNotNull();
        assertThat(proveedorDB.getId()).isGreaterThan(0);
        proveedorService.elininarProveedores();
    }

    @Test
    void obtenerCodigoProveedores() {
        //Given
        proveedorService.guardarProveedor(proveedor1);
        proveedorService.guardarProveedor(proveedor2);
        //When
        List<String> codigos = proveedorService.obtenerCodigoProveedores();
        //Then
        assertThat(codigos).isNotNull();
        proveedorService.elininarProveedores();
    }

    @Test
    void obtenerProovedorByCodigo() {
        //Given
        proveedorService.guardarProveedor(proveedor1);
        //When
        ProveedorEntity proveedorDB = proveedorService.obtenerProovedorByCodigo(proveedor1.getCodigo());
        //Then
        assertThat(proveedorDB).isNotNull();
        assertEquals("13001",proveedorDB.getCodigo());
    }
}