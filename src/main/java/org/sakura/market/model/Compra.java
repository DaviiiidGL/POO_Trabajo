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

    public void agregarLinea(LineaCompra linea) {
        if (lineasCompra == null) {
            lineasCompra = new ArrayList<>();
        }
        lineasCompra.add(linea);
        total = total.add(BigDecimal.valueOf(linea.getSubtotal()));
        System.out.println("Producto agregado: " + linea.getProducto().getNombre() + " | Subtotal actual: " + total);
    }

    public void mostrarDetalleCompra() {
        System.out.println("Compra ID: " + id);
        System.out.println("Fecha: " + fecha);
        System.out.println("Método de pago: " + metodoPago);
        System.out.println("Estado: " + (estadoCompra != null ? estadoCompra : estado));
        System.out.println("Fecha de entrega: " + fechaEntrega);
        System.out.println("Detalle de productos:");
        if (lineasCompra != null && !lineasCompra.isEmpty()) {
            for (LineaCompra linea : lineasCompra) {
                System.out.println("- " + linea.getProducto().getNombre() + " x" + linea.getCantidad() + " = $" + linea.getSubtotal());
            }
        } else {
            System.out.println("No hay productos registrados en esta compra.");
        }
        System.out.println("Total: $" + total);
    }

    public void cambiarEstado(EstadoCompra nuevoEstado) {
        this.estadoCompra = nuevoEstado;
        System.out.println("El estado de la compra ha sido actualizado a: " + nuevoEstado);
    }

    public void confirmarEntrega() {
        this.estadoCompra = EstadoCompra.ENTREGADA;
        this.fechaEntrega = LocalDate.now();
        System.out.println("Compra marcada como ENTREGADA el día " + fechaEntrega);
    }
}
