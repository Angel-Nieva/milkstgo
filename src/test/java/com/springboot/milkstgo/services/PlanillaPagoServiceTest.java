package com.springboot.milkstgo.services;

import com.springboot.milkstgo.entities.AcopioEntity;
import com.springboot.milkstgo.entities.PlanillaPagoEntity;
import com.springboot.milkstgo.repositories.AcopioRepository;
import com.springboot.milkstgo.repositories.PlanillaPagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlanillaPagoServiceTest {
    
    @Autowired
    PlanillaPagoService planillaPagoService;

    @Autowired
    PlanillaPagoRepository planillaPagoRepository;

    private PlanillaPagoEntity planillaPago1, planillaPago2;

    @BeforeEach
    void setUp() throws ParseException {
        planillaPago1 = new PlanillaPagoEntity();
        planillaPago2 = new PlanillaPagoEntity();
        SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy/MM/dd");

        planillaPago1.setQuincena(formato_fecha.parse("2023/03/17"));
        planillaPago1.setCodigo_proveedor("13005");
        planillaPago1.setNombre_proveedor("Alimentos Valle Central 1");
        planillaPago1.setKls_leche(555);
        planillaPago1.setDiasEnvioLeche(13);
        planillaPago1.setAvgKls_leche(43);
        planillaPago1.setVariacion_leche(-8);
        planillaPago1.setGrasa(19);
        planillaPago1.setVariacion_grasa(-41);
        planillaPago1.setSolidos_totales(15);
        planillaPago1.setVariacion_st(-25);
        planillaPago1.setPago_leche(388500);
        planillaPago1.setPago_grasa(16650);
        planillaPago1.setPago_st(-49950);
        planillaPago1.setBonificacion_frecuencia(31080);
        planillaPago1.setDct_leche(0);
        planillaPago1.setDct_grasa(115884);
        planillaPago1.setDct_st(104296);
        planillaPago1.setPago_total(166100);
        planillaPago1.setMonto_retencion(0);
        planillaPago1.setMonto_final(166100);

        planillaPago2.setQuincena(formato_fecha.parse("2023/03/01"));
        planillaPago2.setCodigo_proveedor("13006");
        planillaPago2.setNombre_proveedor("Alimentos Valle Central 2");
        planillaPago2.setKls_leche(600);
        planillaPago2.setDiasEnvioLeche(0);
        planillaPago2.setAvgKls_leche(0);
        planillaPago2.setVariacion_leche(0);
        planillaPago2.setGrasa(32);
        planillaPago2.setVariacion_grasa(0);
        planillaPago2.setSolidos_totales(20);
        planillaPago2.setVariacion_st(0);
        planillaPago2.setPago_leche(0);
        planillaPago2.setPago_grasa(0);
        planillaPago2.setPago_st(0);
        planillaPago2.setBonificacion_frecuencia(0);
        planillaPago2.setDct_leche(0);
        planillaPago2.setDct_grasa(0);
        planillaPago2.setDct_st(0);
        planillaPago2.setPago_total(0);
        planillaPago2.setMonto_retencion(0);
        planillaPago2.setMonto_final(0);

    }

    @Test
    void plantillaDePago() {

    }

    @Test
    void calularPlanillaDePago() {
    }

    @Test
    void borrarPlanillasByFecha() {
        //Given
        planillaPagoRepository.save(planillaPago1);
        Date fecha = java.sql.Date.valueOf("2023-03-17");
        //When
        planillaPagoService.borrarPlanillasByFecha(fecha);
        Optional<PlanillaPagoEntity> planillaPagoEntityOptional = planillaPagoRepository.findById(planillaPago1.getId());
        //Then
        assertThat(planillaPagoEntityOptional).isEmpty();
    }

    @Test
    void obtenerPlantillaPagos() {
        //When
        List<PlanillaPagoEntity> planillasPagosDB = planillaPagoService.obtenerPlantillaPagos();
        
        //Then
        assertThat(planillasPagosDB).isNotNull();
        assertThat(planillasPagosDB.size()).isEqualTo(4);
    }

    @Test
    void pagoCategoria() {
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
    }

    @Test
    void pagoLeche() {
        //When
        Integer pagoLeche = planillaPagoService.pagoLeche("A", 10000);
        //Then
        assertEquals(7000000, pagoLeche);
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