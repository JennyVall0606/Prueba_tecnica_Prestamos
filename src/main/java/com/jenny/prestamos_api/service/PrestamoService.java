package com.jenny.prestamos_api.service;

import com.jenny.prestamos_api.entity.EstadoPrestamo;
import com.jenny.prestamos_api.entity.Prestamo;
import com.jenny.prestamos_api.repository.PrestamoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;

    public PrestamoService(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    public Prestamo solicitarPrestamo(Prestamo prestamo) {
        prestamo.setEstado(EstadoPrestamo.PENDIENTE);
        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo cambiarEstado(Long id, EstadoPrestamo nuevoEstado) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
        prestamo.setEstado(nuevoEstado);
        return prestamoRepository.save(prestamo);
    }

    public List<Prestamo> listarPorUsuario(Long usuarioId) {
        return prestamoRepository.findByUsuarioId(usuarioId);
    }

    public List<Prestamo> listarTodos() {
        return prestamoRepository.findAll();
    }
}
