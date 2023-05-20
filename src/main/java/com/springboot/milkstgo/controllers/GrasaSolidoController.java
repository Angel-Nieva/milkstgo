package com.springboot.milkstgo.controllers;

import com.springboot.milkstgo.entities.GrasaSolidoEntity;
import com.springboot.milkstgo.services.GrasaSolidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping
public class GrasaSolidoController {
    @Autowired
    private GrasaSolidoService grasa_solidoService;

    @GetMapping("/grasas_solidos")
    public String listarGrasa_Solidos(Model model) {
        List<GrasaSolidoEntity> grasas_solidos = grasa_solidoService.obtenerGrasaSolidos();
        model.addAttribute("grasas_solidos",grasas_solidos);
        return "listarGrasas_Solidos";
    }

    @GetMapping("/fileUploadGrasas_Solidos")
    public String main() {
        return "fileUploadGrasas_Solidos";
    }

    @PostMapping("/fileUploadGrasas_Solidos")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        grasa_solidoService.guardarArchivoGrasaSolido(file);
        redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");
        grasa_solidoService.leerCsvGrasaSolido("Grasas.csv");
        return "redirect:/fileUploadGrasas_Solidos";
    }

}
