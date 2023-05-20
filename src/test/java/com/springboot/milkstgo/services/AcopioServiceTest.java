package com.springboot.milkstgo.services;

import com.springboot.milkstgo.entities.AcopioEntity;
import com.springboot.milkstgo.repositories.AcopioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AcopioServiceTest {

    @Autowired
    AcopioRepository acopioRepository;
    @Autowired
    AcopioService acopioService;

    private AcopioEntity acopio1, acopio2;

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
    @Test
    void obtenerAcopios() {
        //Given
        acopioService.guardarAcopio(acopio1);
        acopioService.guardarAcopio(acopio2);
        //When
        List<AcopioEntity> acopiosDB = acopioService.obtenerAcopios();
        //Then
        assertThat(acopiosDB).isNotNull();
        assertThat(acopiosDB.size()).isEqualTo(2);
        acopioRepository.deleteAll();
    }

    @Test
    void guardarAcopio() {
        //Given
        acopioService.guardarAcopio(acopio1);
        //When
        Optional<AcopioEntity> acopioGuardado = acopioRepository.findById(acopio1.getId());
        //Then
        assertThat(acopioGuardado).isNotNull();
        assertThat(acopioGuardado.get().getId()).isPositive();
        acopioRepository.delete(acopio1);
    }

    @Test
    void guardarAcopioDB() throws ParseException {
        //Given
        SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy/MM/dd");
        //When
        acopioService.guardarAcopioDB(
                formato_fecha.parse("2023/03/17"),
                "M",
                "13001",
                "50");
        //Then
        Optional<AcopioEntity> nuevoAcopio = acopioRepository.findById(3L);
        assertThat(nuevoAcopio).isNotNull();
        assertThat(nuevoAcopio.get().getId()).isPositive();
        acopioRepository.delete(nuevoAcopio.get());
    }

    @Test
    void klsLeche() {
        //Given
        acopioService.guardarAcopio(acopio1);
        acopioService.guardarAcopio(acopio2);
        //When
        Integer kls_leche = acopioService.klsLeche(acopio1.getProveedor());
        //Then
        assertEquals(90, kls_leche);
        acopioRepository.deleteAll();

    }

    @Test
    void obtenerQuincenaProveedor() {
        //Given
        acopioService.guardarAcopio(acopio1);
        SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy-MM-dd");

        //When
        Date quincena = acopioService.obtenerQuincenaProveedor(acopio1.getProveedor());
        String quincenaString = formato_fecha.format(quincena);
        //Then
        assertEquals("2023-03-17", quincenaString);
        acopioRepository.delete(acopio1);
    }

    @Test
    void obtenerDiasEnvioLeche() {
        //Given
        acopioService.guardarAcopio(acopio1);
        acopioService.guardarAcopio(acopio2);
        //When
        Integer dias = acopioService.obtenerDiasEnvioLeche(acopio1.getProveedor());
        //Then
        assertEquals(1, dias);
        acopioRepository.deleteAll();
    }

    @Test
    void obtenerPromedioDiarioLeche() {
        //Given
        acopioService.guardarAcopio(acopio1);
        acopioService.guardarAcopio(acopio2);
        //When
        Integer avg = acopioService.obtenerPromedioDiarioLeche(acopio1.getProveedor());
        //Then
        assertEquals(90, avg);
        acopioRepository.deleteAll();
    }

    @Test
    void envioProveedorTarde() {
        //Given
        acopioService.guardarAcopio(acopio1);
        acopioService.guardarAcopio(acopio2);
        //When
        Integer enviosTarde = acopioService.envioProveedorTarde(acopio1.getProveedor());
        //Then
        assertEquals(1,enviosTarde);
        acopioRepository.deleteAll();
    }

    @Test
    void envioProveedorManana() {
        //Given
        acopioService.guardarAcopio(acopio1);
        acopioService.guardarAcopio(acopio2);
        //When
        Integer enviosManana = acopioService.envioProveedorManana(acopio1.getProveedor());
        //Then
        assertEquals(1,enviosManana);
        acopioRepository.deleteAll();
    }

}