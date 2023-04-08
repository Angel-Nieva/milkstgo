package com.springboot.milkstgo.repositories;

import com.springboot.milkstgo.entities.ProveedorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProveedorRepositoryTest {

    @Autowired
    ProveedorRepository proveedorRepository;

    private ProveedorEntity proveedor1;
    private ProveedorEntity proveedor2;

    @BeforeEach
    void setUp() {
        proveedorRepository.deleteAll();
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
    void findAllCodigo() {
        //Given
        proveedorRepository.save(proveedor1);
        proveedorRepository.save(proveedor2);
        //When
        List<String> codigos = proveedorRepository.findAllCodigo();
        //Then
        assertThat(codigos).isNotNull();
        assertThat(codigos.size()).isEqualTo(2);

        proveedorRepository.deleteAll();
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

        proveedorRepository.deleteAll();
    }
}