package com.springboot.milkstgo.services;

import com.springboot.milkstgo.entities.AcopioEntity;
import com.springboot.milkstgo.repositories.AcopioRepository;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class AcopioService {
    @Autowired
    AcopioRepository acopioRepository;

    private final Logger logg = LoggerFactory.getLogger(AcopioService.class);

    public ArrayList<AcopioEntity> obtenerAcopios(){
        return (ArrayList<AcopioEntity>) acopioRepository.findAll();
    }
    public void guardarAcopio(AcopioEntity acopioEntity){
        acopioRepository.save(acopioEntity);
    }

    public void guardarAcopioDB(Date fecha, String turno, String proveedor, String kls_leche) {
        AcopioEntity newData = new AcopioEntity();
        newData.setFecha(fecha);
        newData.setTurno(turno);
        newData.setProveedor(proveedor);
        newData.setKls_leche(kls_leche);
        guardarAcopio(newData);
    }

    @Generated
    public String guardarArchivoAcopio(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");

                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito!";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }

    @Generated
    public void leerCsvAcopio(String direccion){
        String texto = "";
        BufferedReader bf = null;
        acopioRepository.deleteAll();
        SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy/MM/dd");
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    String [] linea = bfRead.split(";");
                    guardarAcopioDB(formato_fecha.parse(linea[0]), linea[1], linea[2], linea[3]);
                    temp = temp + "\n" + bfRead;
                }
            }
            System.out.println("Archivo leido exitosamente");
        }catch(Exception e){
            System.err.println("No se encontro el archivo");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }

    public Integer  klsLeche(String proveedor){
        return acopioRepository.klsLecheByproveedor(proveedor);
    }

    public Date obtenerQuincenaProveedor(String proveedor){return acopioRepository.quincenaByProveedor(proveedor); }

    public Integer obtenerDiasEnvioLeche(String proveedor){return acopioRepository.diasEnvioLeche(proveedor);}

    public Integer obtenerPromedioDiarioLeche(String proveedor){return acopioRepository.avgEnvioLeche(proveedor);}






    // Calcular el pago x kilo de leche segun la categoria del proveedor
    public int pagoCategoria(String categoria){
        return switch (categoria) {
            case "A" -> (700);
            case "B" -> (550);
            case "C" -> (400);
            case "D" -> (250);
            default -> 0;
        };
    }

    public int pagoLeche(String proveedor, String categoria){
        return pagoCategoria(categoria)*klsLeche(proveedor);
    }

    // Calcular el pago x kilo de leche segun % grasa
    public int pagoGrasa(int grasa){
        if ( grasa >= 46  ){
            return 120;
        } else if ( grasa >= 21 ) {
            return 80;
        }else if ( grasa >= 0){
            return 30;
        }else{ // En caso de posibles valores negativos
            return 0;
        }
    }

    // Calcular el pago x kilo de leche segun % solidos totales
    public int pagoSolidos(int solidos){
        if ( solidos >= 36  ){
            return 150;
        } else if ( solidos >= 19 ) {
            return 95;
        } else if ( solidos >= 8){
            return -90;
        } else if ( solidos >= 0) {
            return -130;
        } else{ // En caso de posibles valores negativos
            return 0;
        }
    }

    public int  envioProveedorTarde(String proveedor){
        return acopioRepository.envioProveedorTarde(proveedor);
    }

    public int  envioProveedorManana(String proveedor){
        return acopioRepository.envioProveedorManana(proveedor);
    }

    public int pagoBonificacionFrecuencia(String proveedor, String categoria){
        int enviosTarde  = envioProveedorTarde(proveedor);
        int enviosManana = envioProveedorManana(proveedor);
        int pago         = pagoLeche(proveedor,categoria);
        if (enviosTarde > 10 && enviosManana > 10){
            return (pago/100)*20;
        } else if ( enviosManana > 10 ) {
            return (pago/100)*12;
        } else if ( enviosTarde > 10) {
            return (pago/100)*8;
        } else {
            return 0;
        }
    }

    public int pagoAcopioLeche(String proveedor, String categoria, int grasa, int solidos ){
        return pagoLeche(proveedor,categoria) + pagoGrasa(grasa) + pagoSolidos(solidos)
                + pagoBonificacionFrecuencia(proveedor,categoria);
    }
}
