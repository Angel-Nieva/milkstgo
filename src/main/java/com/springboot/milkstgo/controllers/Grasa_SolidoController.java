package com.springboot.milkstgo.controllers;

import com.springboot.milkstgo.entities.Grasa_SolidoEntity;
import com.springboot.milkstgo.entities.ProveedorEntity;
import com.springboot.milkstgo.services.Grasa_SolidoService;
import com.springboot.milkstgo.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping
public class Grasa_SolidoController {
    @Autowired
    private Grasa_SolidoService grasa_solidoService;

    @GetMapping("/grasas_solidos")
    public String listarGrasa_Solidos(Model model) {
        ArrayList<Grasa_SolidoEntity> grasas_solidos = grasa_solidoService.obtenerGrasas_Solidos();
        model.addAttribute("grasas_solidos",grasas_solidos);
        return "listarGrasas_Solidos";
    }

    @GetMapping("/fileUploadGrasas_Solidos")
    public String main() {
        return "fileUploadGrasas_Solidos";
    }

    @PostMapping("/fileUploadGrasas_Solidos")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        grasa_solidoService.guardarArchivoGrasa_Solido(file);
        redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");
        grasa_solidoService.leerCsvGrasa_Solido("Grasas.csv");
        return "redirect:/fileUploadGrasas_Solidos";
    }

}
