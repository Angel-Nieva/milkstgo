package com.springboot.milkstgo.services;

import com.springboot.milkstgo.entities.AcopioEntity;
import com.springboot.milkstgo.repositories.AcopioRepository;
import com.springboot.milkstgo.repositories.PlanillaPagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlanillaPagoServiceTest {

    @Autowired
    AcopioRepository acopioRepository;
    @Autowired
    PlanillaPagoService planillaPagoService;

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
    void plantillaDePago() {
    }

    @Test
    void calularPlanillaDePago() {
    }

    @Test
    void borrarPlantillasByFecha() {
    }

    @Test
    void obtenerPlantillaPagos() {
    }

    @Test
    void pagoCategoria() {
        //Given
        acopioRepository.save(acopio1);
        //When
        Integer pagoA = planillaPagoService.pagoCategoria("A");
        Integer pagoB = planillaPagoService.pagoCategoria("B");
        Integer pagoC = planillaPagoService.pagoCategoria("C");
        Integer pagoD = planillaPagoService.pagoCategoria("D");
        Integer pagoE = planillaPagoService.pagoCategoria("E");
        //Then
        assertEquals(700, pagoA);
        assertEquals(550, pagoB);
        assertEquals(400, pagoC);
        assertEquals(250, pagoD);
        assertEquals(0, pagoE);
        acopioRepository.delete(acopio1);
    }

    @Test
    void pagoLeche() {
        //Given
        String codigo_proveedor = acopio1.getProveedor();
        acopioRepository.save(acopio1);
        acopioRepository.save(acopio2);
        //When
        Integer pagoLeche = planillaPagoService.pagoLeche("A",
                acopioRepository.klsLecheByproveedor( acopio1.getProveedor()));
        //Then
        assertEquals(63000, pagoLeche);
        acopioRepository.deleteAll();
    }

    @Test
    void pagoGrasa() {
    }

    @Test
    void pagoSolidos() {
    }

    @Test
    void pagoBonificacionFrecuencia() {
    }

    @Test
    void buscarPlanillaPagoAnterior() {
    }

    @Test
    void variacionPorcentual() {
    }

    @Test
    void descuentoLeche() {
    }

    @Test
    void descuentoGrasa() {
    }

    @Test
    void descuentoST() {
    }

    @Test
    void pagoAcopioLeche() {
    }

    @Test
    void pagoTotal() {
    }

    @Test
    void retencion() {
    }
}