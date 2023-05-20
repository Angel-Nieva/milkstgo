package com.springboot.milkstgo.controllers;

import com.springboot.milkstgo.entities.ReporteEntity;
import com.springboot.milkstgo.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    @GetMapping("/planillaPagos")
    public String listarPlantillaPagos(Model model) {
        reporteService.calcularPlantillaPago();
        List<ReporteEntity> planillaPagos = reporteService.obtenerPlanillaPagos();
        model.addAttribute("plantillaPagos",planillaPagos);
        return "listarPagos";
    }
}
