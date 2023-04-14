package com.springboot.milkstgo.services;

import com.springboot.milkstgo.entities.Grasa_SolidoEntity;
import com.springboot.milkstgo.entities.PlanillaPagoEntity;
import com.springboot.milkstgo.entities.ProveedorEntity;
import com.springboot.milkstgo.repositories.PlanillaPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;

@Service
public class PlanillaPagoService {
    @Autowired
    PlanillaPagoRepository planillaPagoRepository;

    @Autowired
    AcopioService acopioService;

    @Autowired
    ProveedorService proveedorService;

    @Autowired
    Grasa_SolidoService grasaSolidoService;

    public void plantillaDePago(){
        //planillaPagoRepository.deleteAll(); // <------------- QUITAR LUEGO
        List<String> codigoProveedores = proveedorService.obtenerCodigoProveedores();
        for (String codigProveedor : codigoProveedores){
            //borrarPlantillasByFecha( (Date) acopioService.obtenerQuincenaProveedor(codigProveedor));
            calularPlanillaDePago(codigProveedor);
        }
    }

    public void calularPlanillaDePago(String codigoProveedor){
        ProveedorEntity proveedor = proveedorService.obtenerProovedorByCodigo(codigoProveedor);
        String codigo = proveedor.getCodigo();
        Grasa_SolidoEntity grasa_solido = grasaSolidoService.buscarPorProveedor(codigo);
        PlanillaPagoEntity pagoAnterior = planillaPagoRepository.findPlanillaAnterior(
                (Date) acopioService.obtenerQuincenaProveedor(codigo),
                codigo);

        PlanillaPagoEntity pagoProveedor = new PlanillaPagoEntity();
        pagoProveedor.setQuincena(acopioService.obtenerQuincenaProveedor(codigo));
        pagoProveedor.setCodigo_proveedor(codigo);
        pagoProveedor.setNombre_proveedor(proveedor.getNombre());
        pagoProveedor.setKls_leche(acopioService.klsLeche(codigo));
        pagoProveedor.setDiasEnvioLeche(acopioService.obtenerDiasEnvioLeche(codigo));
        pagoProveedor.setAvgKls_leche(acopioService.obtenerPromedioDiarioLeche(codigo));
        pagoProveedor.setVariacion_leche(variacionPorcentual(
                pagoProveedor.getKls_leche(),
                pagoAnterior.getKls_leche()));
        pagoProveedor.setGrasa(grasa_solido.getGrasa());
        pagoProveedor.setVariacion_grasa(variacionPorcentual(
                pagoProveedor.getGrasa(),
                pagoAnterior.getGrasa()));
        pagoProveedor.setSolidos_totales(grasa_solido.getSolido());
        pagoProveedor.setVariacion_st(variacionPorcentual(
                pagoProveedor.getSolidos_totales(),
                pagoAnterior.getSolidos_totales()));
        pagoProveedor.setPago_leche(pagoLeche(
                proveedor.getCategoria(),
                pagoProveedor.getKls_leche()));
        pagoProveedor.setPago_grasa(pagoGrasa(
                grasa_solido.getGrasa(),
                pagoProveedor.getKls_leche()));
        pagoProveedor.setPago_st(pagoSolidos(
                grasa_solido.getSolido(),
                pagoProveedor.getKls_leche()));
        pagoProveedor.setBonificacion_frecuencia( pagoBonificacionFrecuencia(
                acopioService.envioProveedorTarde(codigo),
                acopioService.envioProveedorManana(codigo),
                pagoProveedor.getPago_leche() ));

        int pagoAcopioLeche =  pagoAcopioLeche(pagoProveedor.getPago_leche(),
                pagoProveedor.getPago_grasa(),
                pagoProveedor.getPago_st(),
                pagoProveedor.getBonificacion_frecuencia());

        pagoProveedor.setDct_leche(descuentoLeche(
                pagoProveedor.getVariacion_leche(),
                pagoAcopioLeche));
        pagoProveedor.setDct_grasa(descuentoGrasa(
                pagoProveedor.getVariacion_grasa(),
                pagoAcopioLeche));
        pagoProveedor.setDct_st(descuentoST(
                pagoProveedor.getVariacion_st(),
                pagoAcopioLeche));
        pagoProveedor.setPago_total(pagoTotal(
                pagoAcopioLeche,
                pagoProveedor.getDct_grasa()+pagoProveedor.getDct_st()+pagoProveedor.getDct_leche()));
        pagoProveedor.setMonto_retencion(retencion(
                proveedor.getRetencion(),
                pagoProveedor.getPago_total()));
        pagoProveedor.setMonto_final(pagoProveedor.getPago_total() - pagoProveedor.getMonto_retencion());
        planillaPagoRepository.save(pagoProveedor);
    }

    public void borrarPlanillasByFecha(Date fecha){ planillaPagoRepository.deleteByFecha(fecha);}

    public ArrayList<PlanillaPagoEntity> obtenerPlantillaPagos(){
        return (ArrayList<PlanillaPagoEntity>) planillaPagoRepository.findAll();
    }

    public int pagoCategoria(String categoria){
        return switch (categoria) {
            case "A" -> (700);
            case "B" -> (550);
            case "C" -> (400);
            case "D" -> (250);
            default -> 0;
        };
    }
    public int pagoLeche(String categoria, int kls_leche){
        return pagoCategoria(categoria)*kls_leche;
    }

    public int pagoGrasa(int grasa, int kls_leche ){
        if ( grasa >= 46  ){
            return 120*kls_leche;
        } else if ( grasa >= 21 ) {
            return 80*kls_leche;
        }else if ( grasa >= 0){
            return 30*kls_leche;
        }else{ // En caso de posibles valores negativos
            return 0;
        }
    }
    public int pagoSolidos(int solidos, int kls_leche){
        if ( solidos >= 36  ){
            return 150*kls_leche;
        } else if ( solidos >= 19 ) {
            return 95*kls_leche;
        } else if ( solidos >= 8){
            return -90*kls_leche;
        } else if ( solidos >= 0) {
            return -130*kls_leche;
        } else{ // En caso de posibles valores negativos
            return 0;
        }
    }

    public int pagoBonificacionFrecuencia(int enviosTarde, int enviosManana, int pagoLeche){
        if (enviosTarde > 10 && enviosManana > 10){
            return (pagoLeche/100)*20;
        } else if ( enviosManana > 10 ) {
            return (pagoLeche/100)*12;
        } else if ( enviosTarde > 10) {
            return (pagoLeche/100)*8;
        } else {
            return 0;
        }
    }

    public PlanillaPagoEntity buscarPlanillaPagoAnterior(Date fecha, String proveedor){
        return planillaPagoRepository.findPlanillaAnterior(fecha,proveedor);
    }

    public int  variacionPorcentual(int actual, int anterior){
        float variacion = (( (float) actual-anterior)/anterior)*100;
        return round(variacion);
    }

    public int descuentoLeche(int variacion, int pagoAcopio){
        if (variacion < 0){
            variacion = (-1)*variacion;
            if ( variacion >= 46  ){
                return round(((float) pagoAcopio*30)/100);
            } else if ( variacion >= 26 ) {
                return round(((float) pagoAcopio*15)/100);
            }else if ( variacion >= 9){
                return round(((float) pagoAcopio*7)/100);
            }else{
                return 0;
            }
        }
        return 0;
    }

    public int descuentoGrasa(int variacion, int pagoAcopio){
        if (variacion < 0){
            variacion = (-1)*variacion;
            if ( variacion >= 41  ){
                return round(((float) pagoAcopio*30)/100);
            } else if ( variacion >= 26 ) {
                return round(((float) pagoAcopio*20)/100);
            }else if ( variacion >= 16){
                return round(((float) pagoAcopio*12)/100);
            }else{
                return 0;
            }
        }
        return 0;
    }

    public int descuentoST(int variacion, int pagoAcopio){
        if (variacion < 0){
            variacion = (-1)*variacion;
            if ( variacion >= 36  ){
                return round(((float) pagoAcopio*45)/100);
            } else if ( variacion >= 13 ) {
                return round(((float) pagoAcopio*27)/100);
            }else if ( variacion >= 7){
                return round(((float) pagoAcopio*18)/100);
            }else{
                return 0;
            }
        }
        return 0;
    }

    int pagoAcopioLeche(int pagoLeche, int pagoGrasa, int pagoST, int pagoBonificacion){
        return pagoLeche+pagoGrasa+pagoST+pagoBonificacion;
    }


    int pagoTotal(int pagoAcopio, int descuentos){
        return pagoAcopio - descuentos;
    }

    int retencion(String retencion ,int pagoTotal){
        if( retencion == "Si" && pagoTotal > 950000){
            return round(((float) pagoTotal*13)/100);
        }
        return 0;
    }
}
