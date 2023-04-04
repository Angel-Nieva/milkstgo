package com.springboot.milkstgo.services;

import com.springboot.milkstgo.entities.Grasa_SolidoEntity;
import com.springboot.milkstgo.repositories.Grasa_SolidoRepository;
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
import java.text.ParseException;
import java.util.ArrayList;

@Service
public class Grasa_SolidoService {
    @Autowired
    Grasa_SolidoRepository grasa_solidoRepository;

    private final Logger logg = LoggerFactory.getLogger(Grasa_SolidoService.class);

    public ArrayList<Grasa_SolidoEntity> obtenerGrasas_Solidos(){
        return (ArrayList<Grasa_SolidoEntity>) grasa_solidoRepository.findAll();
    }
    public void guardarGrasa_Solido(Grasa_SolidoEntity Grasa_SolidoEntity){
        grasa_solidoRepository.save(Grasa_SolidoEntity);
    }

    public void guardarGrasa_SolidoDB(String proveedor, Integer grasa, Integer solido) throws ParseException {
        Grasa_SolidoEntity newData = new Grasa_SolidoEntity();
        newData.setProveedor(proveedor);
        newData.setGrasa(grasa);
        newData.setSolido(solido);
        guardarGrasa_Solido(newData);
    }

    @Generated
    public String guardarArchivoGrasa_Solido(MultipartFile file){
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
    public void leerCsvGrasa_Solido(String direccion){
        String texto = "";
        BufferedReader bf = null;
        grasa_solidoRepository.deleteAll();
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
                    guardarGrasa_SolidoDB(linea[0], Integer.parseInt(linea[1]), Integer.parseInt(linea[2]));
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
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

    public Grasa_SolidoEntity buscarPorProveedor(String proveedor){return grasa_solidoRepository.findByProveedor(proveedor);}
}
