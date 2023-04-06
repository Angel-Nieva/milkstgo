package com.springboot.milkstgo.repositories;

import com.springboot.milkstgo.entities.AcopioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class AcopioRepositoryTest {

    @Autowired
    private AcopioRepository acopioRepository;

    private AcopioEntity acopio;

    @BeforeEach
    void setUp() throws ParseException {
        AcopioEntity acopio = new AcopioEntity();
        SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy/MM/dd");
        acopio.setFecha(formato_fecha.parse("2023/03/17"));
        acopio.setTurno("M");
        acopio.setProveedor("13001");
        acopio.setKls_leche("50");
    }

    @Test
    void klsLecheByproveedor() {
        //Given
        acopioRepository.save(acopio);
        //When
        Integer kls_leche = acopioRepository.klsLecheByproveedor(acopio.getProveedor());
        assertEquals(50, kls_leche);
        acopioRepository.delete(acopio);
    }

    @Test
    void envioProveedorManana() {
    }

    @Test
    void envioProveedorTarde() {
    }

    @Test
    void quincenaByProveedor() {
    }

    @Test
    void diasEnvioLeche() {
    }

    @Test
    void avgEnvioLeche() {
    }
}