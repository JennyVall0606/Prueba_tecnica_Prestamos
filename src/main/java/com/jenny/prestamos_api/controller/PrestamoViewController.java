package com.jenny.prestamos_api.controller;

import com.jenny.prestamos_api.entity.EstadoPrestamo;
import com.jenny.prestamos_api.service.PrestamoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PrestamoViewController {

    private final PrestamoService prestamoService;

    public PrestamoViewController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("prestamos", prestamoService.listarTodos());
        return "index";
    }

    @PostMapping("/solicitar")
    public String solicitar(@RequestParam Double monto, @RequestParam Integer plazoMeses) {
        com.jenny.prestamos_api.entity.Prestamo p = new com.jenny.prestamos_api.entity.Prestamo();
        p.setMonto(java.math.BigDecimal.valueOf(monto));
        p.setPlazoMeses(plazoMeses);
        prestamoService.solicitarPrestamo(p);
        return "redirect:/";
    }

    @PostMapping("/cambiar-estado")
    public String cambiarEstado(@RequestParam Long id, @RequestParam EstadoPrestamo estado) {
        prestamoService.cambiarEstado(id, estado);
        return "redirect:/";
    }
}