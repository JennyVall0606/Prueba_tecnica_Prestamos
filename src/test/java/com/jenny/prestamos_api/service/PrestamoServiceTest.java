package com.jenny.prestamos_api.service;

import com.jenny.prestamos_api.entity.EstadoPrestamo;
import com.jenny.prestamos_api.entity.Prestamo;
import com.jenny.prestamos_api.repository.PrestamoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceTest {

    @Mock
    private PrestamoRepository prestamoRepository;

    @InjectMocks
    private PrestamoService prestamoService;

    @Test
    void solicitarPrestamo_deberiaGuardarConEstadoPendiente() {
        Prestamo prestamo = new Prestamo();
        prestamo.setMonto(BigDecimal.valueOf(1000));
        prestamo.setPlazoMeses(12);

        when(prestamoRepository.save(any(Prestamo.class))).thenReturn(prestamo);

        Prestamo resultado = prestamoService.solicitarPrestamo(prestamo);

        assertEquals(EstadoPrestamo.PENDIENTE, resultado.getEstado());
        verify(prestamoRepository, times(1)).save(prestamo);
    }

    @Test
    void cambiarEstado_deberiaActualizarEstadoDelPrestamo() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setEstado(EstadoPrestamo.PENDIENTE);

        when(prestamoRepository.findById(1L)).thenReturn(Optional.of(prestamo));
        when(prestamoRepository.save(any(Prestamo.class))).thenReturn(prestamo);

        Prestamo resultado = prestamoService.cambiarEstado(1L, EstadoPrestamo.APROBADO);

        assertEquals(EstadoPrestamo.APROBADO, resultado.getEstado());
        verify(prestamoRepository, times(1)).save(prestamo);
    }

    @Test
    void cambiarEstado_deberiaLanzarExcepcion_siPrestamoNoExiste() {
        when(prestamoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                prestamoService.cambiarEstado(99L, EstadoPrestamo.APROBADO)
        );
    }
}