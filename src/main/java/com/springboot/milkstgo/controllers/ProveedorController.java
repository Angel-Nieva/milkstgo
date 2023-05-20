package com.springboot.milkstgo.controllers;

import com.springboot.milkstgo.entities.ProveedorEntity;
import com.springboot.milkstgo.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorServices;

    @GetMapping({"/proveedores", "/"})
    public String listarProveedores(Model model) {
        List<ProveedorEntity> proveedores = proveedorServices.obtenerProveedores();
        model.addAttribute("proveedores",proveedores);
        return "listarProveedores";
    }

    @GetMapping("/proveedores/nuevo")
    public String crearProveedorFormulario(Model model){
        ProveedorEntity proveedor = new ProveedorEntity();
        model.addAttribute("proveedor", proveedor);
        return "crearProveedor";
    }

    @PostMapping("/proveedores")
    public String guardarProveedor(@ModelAttribute("proveedor") ProveedorEntity proveedor){
        proveedorServices.guardarProveedor(proveedor);
        return "redirect:/listarProveedores";
    }


}
