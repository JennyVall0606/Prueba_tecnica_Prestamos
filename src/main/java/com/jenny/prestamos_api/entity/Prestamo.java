package com.jenny.prestamos_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "prestamos")
@Data
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 1, message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    @NotNull
    @Min(value = 1, message = "El plazo debe ser mínimo 1 mes")
    private Integer plazoMeses;

    @Enumerated(EnumType.STRING)
    private EstadoPrestamo estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}