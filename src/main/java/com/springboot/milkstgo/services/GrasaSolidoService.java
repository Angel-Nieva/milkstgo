package com.springboot.milkstgo.services;

import com.springboot.milkstgo.entities.GrasaSolidoEntity;
import com.springboot.milkstgo.repositories.GrasaSolidoRepository;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class GrasaSolidoService {
    @Autowired
    GrasaSolidoRepository grasaSolidoRepository;

    private final Logger logg = LoggerFactory.getLogger(GrasaSolidoService.class);

    public List<GrasaSolidoEntity> obtenerGrasaSolidos(){
        return (ArrayList<GrasaSolidoEntity>) grasaSolidoRepository.findAll();
    }
    public void guardarGrasaSolido(GrasaSolidoEntity grasaSolidoEntity){
        grasaSolidoRepository.save(grasaSolidoEntity);
    }

    public void guardarGrasaSolidoDB(String proveedor, Integer grasa, Integer solido) {
        GrasaSolidoEntity newData = new GrasaSolidoEntity();
        newData.setProveedor(proveedor);
        newData.setGrasa(grasa);
        newData.setSolido(solido);
        guardarGrasaSolido(newData);
    }

    @Generated
    public String guardarArchivoGrasaSolido(MultipartFile file){
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
    public void leerCsvGrasaSolido(String direccion){
        String texto = "";
        BufferedReader bf = null;
        grasaSolidoRepository.deleteAll();
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
                    guardarGrasaSolidoDB(linea[0], Integer.parseInt(linea[1]), Integer.parseInt(linea[2]));
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

    public GrasaSolidoEntity buscarPorProveedor(String proveedor){return grasaSolidoRepository.findByProveedor(proveedor);}
}
