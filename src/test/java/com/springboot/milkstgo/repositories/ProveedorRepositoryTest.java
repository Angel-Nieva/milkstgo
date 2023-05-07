package com.springboot.milkstgo.repositories;

import com.springboot.milkstgo.entities.ProveedorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProveedorRepositoryTest {

    @Autowired
    ProveedorRepository proveedorRepository;

    private ProveedorEntity proveedor1;

    @BeforeEach
    void setUp() {
        proveedor1 = new ProveedorEntity();
        proveedor1.setCategoria("A");
        proveedor1.setCodigo("13007");
        proveedor1.setNombre("Alimentos Valle Central");
        proveedor1.setRetencion("No");

    }

    @Test
    void findAllCodigo() {
        //When
        List<String> codigos = proveedorRepository.findAllCodigo();
        //Then
        assertThat(codigos).isNotNull();
        assertThat(codigos.size()).isEqualTo(3);

    }

    @Test
    void findProveedorByCodigo() {
        //Given
        proveedorRepository.save(proveedor1);
        //When
        ProveedorEntity proveedor = proveedorRepository.findProveedorByCodigo(proveedor1.getCodigo());
        //Then
        assertThat(proveedor).isNotNull();
        assertThat(proveedor.getId()).isGreaterThan(0);

        proveedorRepository.delete(proveedor1);
    }
}