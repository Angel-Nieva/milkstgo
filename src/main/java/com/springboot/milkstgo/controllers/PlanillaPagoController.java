package com.springboot.milkstgo.controllers;

import com.springboot.milkstgo.entities.PlanillaPagoEntity;
import com.springboot.milkstgo.entities.ProveedorEntity;
import com.springboot.milkstgo.services.PlanillaPagoService;
import com.springboot.milkstgo.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping
public class PlanillaPagoController {
    @Autowired
    private PlanillaPagoService planillaPagoService;

    @GetMapping("/plantillaPagos")
    public String listarPlantillaPagos(Model model) {
        planillaPagoService.plantillaDePago();
        ArrayList<PlanillaPagoEntity> plantillaPagos = planillaPagoService.obtenerPlantillaPagos();
        model.addAttribute("plantillaPagos",plantillaPagos);
        return "listarPagos";
    }
}
