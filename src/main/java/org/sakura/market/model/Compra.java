package org.sakura.market.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Compra {
    private EstadoCompra estadoCompra;
    private UUID id;
    private LocalDate fecha;
    private BigDecimal total;
    private List<LineaCompra> lineasCompra;
    private MetodoPago metodoPago;
    private EstadoCompra estado;
    private LocalDate fechaEntrega;

    public Compra(MetodoPago metodoPago){
        this.id = UUID.randomUUID();
        this.fecha = LocalDate.now();
        this.metodoPago = metodoPago;
        this.estado = EstadoCompra.PENDIENTE;
        this.lineasCompra = new ArrayList<LineaCompra>();
        this.total = BigDecimal.ZERO;
    }

    public Compra(LocalDate fecha, MetodoPago metodoPago, EstadoCompra estadoCompra, LocalDate fechaEntrega){
        this.id = UUID.randomUUID();
        this.fecha = fecha;
        this.metodoPago = metodoPago;
        this.estadoCompra = estadoCompra;
        this.fechaEntrega = fechaEntrega;
    }

}
