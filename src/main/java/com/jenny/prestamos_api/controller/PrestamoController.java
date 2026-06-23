package com.jenny.prestamos_api.controller;

import com.jenny.prestamos_api.entity.EstadoPrestamo;
import com.jenny.prestamos_api.entity.Prestamo;
import com.jenny.prestamos_api.service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public Prestamo solicitar(@Valid @RequestBody Prestamo prestamo) {
        return prestamoService.solicitarPrestamo(prestamo);
    }

    @PutMapping("/{id}/estado")
    public Prestamo cambiarEstado(@PathVariable Long id, @RequestParam EstadoPrestamo estado) {
        return prestamoService.cambiarEstado(id, estado);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Prestamo> porUsuario(@PathVariable Long usuarioId) {
        return prestamoService.listarPorUsuario(usuarioId);
    }

    @GetMapping
    public List<Prestamo> todos() {
        return prestamoService.listarTodos();
    }
}