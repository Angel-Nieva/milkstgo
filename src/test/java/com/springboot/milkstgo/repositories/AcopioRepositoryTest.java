package com.springboot.milkstgo.repositories;

import com.springboot.milkstgo.entities.AcopioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AcopioRepositoryTest {

    @Autowired
    AcopioRepository acopioRepository;

    private AcopioEntity acopio1;
    private AcopioEntity acopio2;

    @BeforeEach
    void setUp() throws ParseException {
        acopio1 = new AcopioEntity();
        acopio2 = new AcopioEntity();
        SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy/MM/dd");
        acopio1.setFecha(formato_fecha.parse("2023/03/17"));
        acopio1.setTurno("M");
        acopio1.setProveedor("13001");
        acopio1.setKls_leche("50");

        acopio2.setFecha(formato_fecha.parse("2023/03/17"));
        acopio2.setTurno("T");
        acopio2.setProveedor("13001");
        acopio2.setKls_leche("40");
    }

    @DisplayName("Test para calcular los Kilos de leche de un acopio ")
    @Test
    void klsLecheByproveedor(){
        //Given
        acopioRepository.save(acopio1);
        acopioRepository.save(acopio2);
        //When
        Integer kls_leche = acopioRepository.klsLecheByproveedor(acopio1.getProveedor());
        //Then
        assertEquals(90, kls_leche);
        acopioRepository.deleteAll();
    }

    @DisplayName("Test para calcular los acopios de un proveedor en la mañana")
    @Test
    void envioProveedorManana() {
        //Given
        acopioRepository.save(acopio1);
        //When
        Integer enviosManana = acopioRepository.envioProveedorManana(acopio1.getProveedor());
        //Then
        assertEquals(1, enviosManana);
        acopioRepository.delete(acopio1);
    }
    @DisplayName("Test para calcular los acopios de un proveedor en la tarde")
    @Test
    void envioProveedorTarde() {
        //Given
        acopioRepository.save(acopio2);
        //When
        Integer enviosTarde = acopioRepository.envioProveedorTarde(acopio2.getProveedor());
        //Then
        assertEquals(1, enviosTarde);
        acopioRepository.delete(acopio2);
    }

    @DisplayName("Test para calcular la quincena del acopio")
    @Test
    void quincenaByProveedor() {
        //Given
        acopioRepository.save(acopio1);
        SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy-MM-dd");

        //When
        Date enviosTarde = acopioRepository.quincenaByProveedor(acopio1.getProveedor());
        String a = formato_fecha.format(enviosTarde);
        //Then
        assertEquals("2023-03-17", a);
        acopioRepository.delete(acopio1);
    }

    @DisplayName("Test para calcular los dias en un acopio de un proveedor")
    @Test
    void diasEnvioLeche() {
        //Given
        acopioRepository.save(acopio1);
        acopioRepository.save(acopio2);

        //When
        int dias = acopioRepository.diasEnvioLeche(acopio1.getProveedor());
        //Then
        assertEquals(1, dias);
        acopioRepository.deleteAll();
    }

    @Test
    void avgEnvioLeche() {
        //Given
        acopioRepository.save(acopio1);
        acopioRepository.save(acopio2);

        //When
        int avgLecheDiaria = acopioRepository.avgEnvioLeche(acopio1.getProveedor());
        //Then
        assertEquals(90, avgLecheDiaria);
        acopioRepository.deleteAll();
    }
}