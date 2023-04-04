package com.springboot.milkstgo.controllers;

import com.springboot.milkstgo.entities.AcopioEntity;
import com.springboot.milkstgo.services.AcopioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping
public class AcopioController {

    @Autowired
    AcopioService acopioServices;

    @GetMapping("/acopios")
    public String listarAcopios(Model model) {
        ArrayList<AcopioEntity> acopios = acopioServices.obtenerAcopios();
        model.addAttribute("acopios",acopios);
        return "listarAcopios";
    }

    @GetMapping("/fileUploadAcopios")
    public String main() {
        return "fileUploadAcopios";
    }

    @PostMapping("/fileUploadAcopios")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        acopioServices.guardarArchivoAcopio(file);
        redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");
        acopioServices.leerCsvAcopio("Acopio.csv");
        return "redirect:/fileUploadAcopios";
    }

}
