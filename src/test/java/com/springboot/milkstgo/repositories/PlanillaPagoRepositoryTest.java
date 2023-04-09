package com.springboot.milkstgo.repositories;

import com.springboot.milkstgo.entities.PlanillaPagoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PlanillaPagoRepositoryTest {

    @Autowired
    PlanillaPagoRepository planillaPagoRepository;

    PlanillaPagoEntity planillaPago;
    PlanillaPagoEntity planillaPago2;

    @BeforeEach
    void setUp() throws ParseException {
        planillaPagoRepository.deleteAll();
        planillaPago = new PlanillaPagoEntity();
        SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy/MM/dd");
        planillaPago.setQuincena(formato_fecha.parse("2023/03/17"));
        planillaPago.setCodigo_proveedor("13001");
        planillaPago.setNombre_proveedor("Alimentos Valle Central");
        planillaPago.setKls_leche(555);
        planillaPago.setDiasEnvioLeche(13);
        planillaPago.setAvgKls_leche(43);
        planillaPago.setVariacion_leche(-8);
        planillaPago.setGrasa(19);
        planillaPago.setVariacion_grasa(-41);
        planillaPago.setSolidos_totales(15);
        planillaPago.setVariacion_st(-25);
        planillaPago.setPago_leche(388500);
        planillaPago.setPago_grasa(16650);
        planillaPago.setPago_st(-49950);
        planillaPago.setBonificacion_frecuencia(31080);
        planillaPago.setDct_leche(0);
        planillaPago.setDct_grasa(115884);
        planillaPago.setDct_st(104296);
        planillaPago.setPago_total(166100);
        planillaPago.setMonto_retencion(0);
        planillaPago.setMonto_final(166100);
    }

    @Test
    void deleteByFecha() {
        //Given
        planillaPagoRepository.save(planillaPago);
        Date fecha = java.sql.Date.valueOf("2023-03-17");
        //When
        planillaPagoRepository.deleteByFecha(fecha);
        Optional<PlanillaPagoEntity> planillaPagoEntityOptional = planillaPagoRepository.findById(planillaPago.getId());
        //Then
        assertThat(planillaPagoEntityOptional).isEmpty();
    }

    @Test
    void findPlanillaAnterior() throws ParseException {
        //Given

        planillaPago2 = new PlanillaPagoEntity();
        SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy/MM/dd");
        planillaPago2.setQuincena(formato_fecha.parse("2023/02/17"));
        planillaPago2.setCodigo_proveedor("13001");
        planillaPago2.setNombre_proveedor("Alimentos Valle Central");
        planillaPago2.setKls_leche(555);
        planillaPago2.setDiasEnvioLeche(13);
        planillaPago2.setAvgKls_leche(43);
        planillaPago2.setVariacion_leche(-8);
        planillaPago2.setGrasa(19);
        planillaPago2.setVariacion_grasa(-41);
        planillaPago2.setSolidos_totales(15);
        planillaPago2.setVariacion_st(-25);
        planillaPago2.setPago_leche(388500);
        planillaPago2.setPago_grasa(16650);
        planillaPago2.setPago_st(-49950);
        planillaPago2.setBonificacion_frecuencia(31080);
        planillaPago2.setDct_leche(0);
        planillaPago2.setDct_grasa(115884);
        planillaPago2.setDct_st(104296);
        planillaPago2.setPago_total(166100);
        planillaPago2.setMonto_retencion(0);
        planillaPago2.setMonto_final(166100);

        planillaPagoRepository.save(planillaPago);
        planillaPagoRepository.save(planillaPago2);
        //When
        Date fecha = java.sql.Date.valueOf("2023-03-17");
        PlanillaPagoEntity planillaPagoAnterior =
                planillaPagoRepository.findPlanillaAnterior(fecha,
                        planillaPago.getCodigo_proveedor());
        //Then
        assertThat(planillaPagoAnterior).isNotNull();
        assertThat(planillaPagoAnterior.getId()).isGreaterThan(0);
        planillaPagoRepository.deleteAll();
    }
}