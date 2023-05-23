package com.springboot.milkstgo.services;

import com.springboot.milkstgo.entities.AcopioEntity;
import com.springboot.milkstgo.entities.ReporteEntity;
import com.springboot.milkstgo.entities.GrasaSolidoEntity;
import com.springboot.milkstgo.repositories.AcopioRepository;
import com.springboot.milkstgo.repositories.GrasaSolidoRepository;
import com.springboot.milkstgo.repositories.ReporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ReporteServiceTest {
    
    @Autowired
    ReporteService reporteService;
    @Autowired
    ReporteRepository reporteRepository;

    @Autowired
    AcopioService acopioService;
    @Autowired
    AcopioRepository acopioRepository;

    @Autowired
    GrasaSolidoService grasaSolidoService;

    @Autowired
    GrasaSolidoRepository grasaSolidoRepository;

    private ReporteEntity reportePago1;

    @BeforeEach
    void setUp() throws ParseException {
        reportePago1 = new ReporteEntity();
        SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy/MM/dd");

        reportePago1.setQuincena(formato_fecha.parse("2023/03/17"));
        reportePago1.setCodigo_proveedor("13005");
        reportePago1.setNombre_proveedor("Alimentos Valle Central 1");
        reportePago1.setKls_leche(555);
        reportePago1.setDiasEnvioLeche(13);
        reportePago1.setAvgKls_leche(43);
        reportePago1.setVariacion_leche(-8);
        reportePago1.setGrasa(19);
        reportePago1.setVariacion_grasa(-41);
        reportePago1.setSolidos_totales(15);
        reportePago1.setVariacion_st(-25);
        reportePago1.setPago_leche(388500);
        reportePago1.setPago_grasa(16650);
        reportePago1.setPago_st(-49950);
        reportePago1.setBonificacion_frecuencia(31080);
        reportePago1.setDct_leche(0);
        reportePago1.setDct_grasa(115884);
        reportePago1.setDct_st(104296);
        reportePago1.setPago_total(166100);
        reportePago1.setMonto_retencion(0);
        reportePago1.setMonto_final(166100);
    }

    @Test
    void calularReporte() {
        //Given
        AcopioEntity acopio = new AcopioEntity();
        Date fecha = java.sql.Date.valueOf("2023-03-17");
        acopio.setFecha(fecha);
        acopio.setTurno("M");
        acopio.setProveedor("13001");
        acopio.setKls_leche("100");

        GrasaSolidoEntity grasaSolido = new GrasaSolidoEntity();
        grasaSolido.setGrasa(19);
        grasaSolido.setProveedor("13001");
        grasaSolido.setSolido(15);
        grasaSolidoService.guardarGrasaSolido(grasaSolido);

        acopioService.guardarAcopio(acopio);
        //When
        ReporteEntity nuevoReporte = reporteService.calularReporte("13001");
        //Then
        assertEquals(8320,nuevoReporte.getMonto_final());
        acopioRepository.delete(acopio);
        grasaSolidoRepository.delete(grasaSolido);
        reporteRepository.delete(nuevoReporte);

    }

    @Test
    void borrarPlanillasByFecha() {
        //Given
        reporteRepository.save(reportePago1);
        Date fecha = java.sql.Date.valueOf("2023-03-17");
        //When
        reporteService.borrarReporteByFecha(fecha);
        Optional<ReporteEntity> planillaPagoEntityOptional = reporteRepository.findById(reportePago1.getId());
        //Then
        assertThat(planillaPagoEntityOptional).isEmpty();
    }

    @Test
    void obtenerPlantillaPagos() {
        //When
        List<ReporteEntity> planillasPagosDB = reporteService.obtenerPlanillaPagos();
        
        //Then
        assertThat(planillasPagosDB).isNotNull();
        assertThat(planillasPagosDB.size()).isEqualTo(4);
    }

    @Test
    void pagoCategoria() {
        //When
        Integer pagoA = reporteService.pagoCategoria("A");
        Integer pagoB = reporteService.pagoCategoria("B");
        Integer pagoC = reporteService.pagoCategoria("C");
        Integer pagoD = reporteService.pagoCategoria("D");
        Integer pagoE = reporteService.pagoCategoria("E");
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
        Integer pagoLeche = reporteService.pagoLeche("A", 10000);
        //Then
        assertEquals(7000000, pagoLeche);
    }

    @Test
    void pagoGrasa() {
        //When
        Integer pagoGrasa_46 = reporteService.pagoGrasa(50, 10);
        Integer pagoGrasa_21 = reporteService.pagoGrasa(30, 10);
        Integer pagoGrasa_0 = reporteService.pagoGrasa(10, 10);
        Integer pagoGrasa_negativo = reporteService.pagoGrasa(-50, 10);
        //Then
        assertEquals(1200, pagoGrasa_46);
        assertEquals(800, pagoGrasa_21);
        assertEquals(300, pagoGrasa_0);
        assertEquals(0, pagoGrasa_negativo);
    }

    @Test
    void pagoSolidos() {
        //When
        Integer pagoSolido_36 = reporteService.pagoSolidos(36, 10);
        Integer pagoSolido_19 = reporteService.pagoSolidos(19, 10);
        Integer pagoSolido_8 = reporteService.pagoSolidos(8, 10);
        Integer pagoSolido_0 = reporteService.pagoSolidos(0, 10);
        Integer pagoSolido_negativo = reporteService.pagoSolidos(-100, 10);
        //Then
        assertEquals(1500, pagoSolido_36);
        assertEquals(950, pagoSolido_19);
        assertEquals(-900, pagoSolido_8);
        assertEquals(-1300, pagoSolido_0);
        assertEquals(0, pagoSolido_negativo);
    }

    @Test
    void pagoBonificacionFrecuencia() {
        //When
        Integer pagoBonificacionMananaTarde = reporteService.pagoBonificacionFrecuencia
                (20,20, 1000);
        Integer pagoBonificacionManana = reporteService.pagoBonificacionFrecuencia
                (0,20, 1000);
        Integer pagoBonificacionTarde = reporteService.pagoBonificacionFrecuencia
                (20,0, 1000);
        Integer pagoBonificacionNulo = reporteService.pagoBonificacionFrecuencia
                (0,0, 1000);
        //Then
        assertEquals(200, pagoBonificacionMananaTarde);
        assertEquals(120, pagoBonificacionManana);
        assertEquals(80, pagoBonificacionTarde);
        assertEquals(0, pagoBonificacionNulo);
    }

    @Test
    void buscarPlanillaPagoAnterior() {
        //Given
        reporteRepository.save(reportePago1);
        Date quincenaActual = reportePago1.getQuincena();
        SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy/MM/dd");

        //When
        ReporteEntity reporteAnterior = reporteService.buscarReporteAnterior(
                quincenaActual,
                reportePago1.getCodigo_proveedor());

        //Then
        assertEquals("2023/03/01", formato_fecha.format(reporteAnterior.getQuincena()));
        reporteRepository.delete(reportePago1);

    }

    @Test
    void variacionPorcentual() {
        //When
        int variacion_positiva = reporteService.variacionPorcentual(1000,100);
        int variacion_negativa = reporteService.variacionPorcentual(100,1000);
        assertEquals(900,variacion_positiva);
        assertEquals(-90,variacion_negativa);
    }

    @Test
    void descuentoLeche() {
        //When
        int variacion_positiva = reporteService.descuento("leche",10, 100);
        int variacion_46 = reporteService.descuento("leche",-46, 100);
        int variacion_26 = reporteService.descuento("leche",-26, 100);
        int variacion_9  = reporteService.descuento("leche",-9, 100);
        int variacion_1  = reporteService.descuento("leche",-1, 100);
        //Then
        assertEquals(0,variacion_positiva);
        assertEquals(30,variacion_46);
        assertEquals(15,variacion_26);
        assertEquals(7,variacion_9);
        assertEquals(0,variacion_1);

    }
    @Test
    void descuentoGrasa() {
        int variacion_positiva = reporteService.descuento("grasa",10, 100);
        int variacion_41 = reporteService.descuento("grasa",-41, 100);
        int variacion_26 = reporteService.descuento("grasa",-26, 100);
        int variacion_16  = reporteService.descuento("grasa",-16, 100);
        int variacion_1  = reporteService.descuento("grasa",-1, 100);
        //Then
        assertEquals(0,variacion_positiva);
        assertEquals(30,variacion_41);
        assertEquals(20,variacion_26);
        assertEquals(12,variacion_16);
        assertEquals(0,variacion_1);
    }

    @Test
    void descuentoST() {
        int variacion_positiva = reporteService.descuento("solido",10, 100);
        int variacion_36 = reporteService.descuento("solido",-36, 100);
        int variacion_13 = reporteService.descuento("solido",-13, 100);
        int variacion_7  = reporteService.descuento("solido",-7, 100);
        int variacion_1  = reporteService.descuento("solido",-1, 100);
        //Then
        assertEquals(0,variacion_positiva);
        assertEquals(45,variacion_36);
        assertEquals(27,variacion_13);
        assertEquals(18,variacion_7);
        assertEquals(0,variacion_1);
    }

    @Test
    void pagoAcopioLeche() {
        //Given
        reporteRepository.save(reportePago1);
        //When
        int pagoAcopio = reporteService.pagoAcopioLeche(
                reportePago1.getPago_leche(),
                reportePago1.getPago_grasa(),
                reportePago1.getPago_st(),
                reportePago1.getBonificacion_frecuencia());
        //Then
        assertEquals(386280,pagoAcopio);
        reporteRepository.delete(reportePago1);
    }

    @Test
    void pagoTotal() {
        //When
        int pagoTotal = reporteService.pagoTotal(1000,50);
        //Then
        assertEquals(950,pagoTotal);
    }

    @Test
    void retencion() {
        //When
        int retencion_si = reporteService.retencion("Si",950001);
        int retencion_no = reporteService.retencion("No",950000);
        //Then
        assertEquals(123500,retencion_si);
        assertEquals(0,retencion_no);
    }
}