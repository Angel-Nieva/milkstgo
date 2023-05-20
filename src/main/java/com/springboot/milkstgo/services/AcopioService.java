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
import java.util.List;

@Service
public class AcopioService {
    @Autowired
    AcopioRepository acopioRepository;

    private final Logger logg = LoggerFactory.getLogger(AcopioService.class);

    public List<AcopioEntity> obtenerAcopios(){
        return (ArrayList<AcopioEntity>) acopioRepository.findAll();
    }
    public void guardarAcopio(AcopioEntity acopioEntity){
        acopioRepository.save(acopioEntity);
    }

    public void guardarAcopioDB(Date fecha, String turno, String proveedor, String klsLeche) {
        AcopioEntity newData = new AcopioEntity();
        newData.setFecha(fecha);
        newData.setTurno(turno);
        newData.setProveedor(proveedor);
        newData.setKls_leche(klsLeche);
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
        BufferedReader bf = null;
        acopioRepository.deleteAll();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
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
                    guardarAcopioDB(formatoFecha.parse(linea[0]), linea[1], linea[2], linea[3]);
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

    // Calcula la cantidad de envios durante la tarde para el ultimo acopio
    public int  envioProveedorTarde(String proveedor){
        return acopioRepository.envioProveedorTarde(proveedor);
    }

    // Calcula la cantidad de envios durante la mañana para el ultimo acopio
    public int  envioProveedorManana(String proveedor){
        return acopioRepository.envioProveedorManana(proveedor);
    }

}
