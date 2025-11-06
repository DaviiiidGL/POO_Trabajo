package models.shopping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.users.Cliente;
import utils.IO;

public class Compra {
    private long id;
    private LocalDate fechaCompra;
    private Cliente cliente;
    private List<LineaCompra> lineasCompra;
    private EstadoCompra estado;
    private MetodoPago metodoPago;
    private Direccion direccionEnvio;

    public Compra(long id, LocalDate fechaCompra, Cliente cliente) {
        this.id = id;
        this.fechaCompra = fechaCompra;
        this.cliente = cliente;
        this.lineasCompra = new ArrayList<>();
        this.estado = EstadoCompra.PENDIENTE;
    }

    public long getId() { return id; }
    public LocalDate getFechaCompra() { return fechaCompra; }
    public Cliente getCliente() { return cliente; }
    public List<LineaCompra> getLineasCompra() { return new ArrayList<>(lineasCompra); }
    public EstadoCompra getEstado() { return estado; }
    public MetodoPago getMetodoPago() { return metodoPago; }
    public Direccion getDireccionEnvio() { return direccionEnvio; }

    public void setEstado(EstadoCompra estado) {
        this.estado = estado;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setDireccionEnvio(Direccion direccion) {
        this.direccionEnvio = direccion;
    }

    public void agregarLineaCompra(LineaCompra lineaCompra) {
        lineasCompra.add(lineaCompra);
    }

    public double getTotal() {
        return lineasCompra.stream()
                .mapToDouble(LineaCompra::getSubtotal)
                .sum();
    }

    public void mostrarInfo() {
        IO.println("\n=== Compra #" + id + " ===");
        IO.println("Fecha: " + fechaCompra);
        IO.println("Cliente: " + cliente.getNombre());
        IO.println("Estado: " + estado);
        
        if (metodoPago != null) {
            IO.println("\nMétodo de pago:");
            metodoPago.mostrarInfo();
        }
        
        if (direccionEnvio != null) {
            IO.println("\nDirección de envío:");
            direccionEnvio.mostrarInfo();
        }
        
        IO.println("\nProductos:");
        for (LineaCompra lc : lineasCompra) {
            IO.println("- " + lc.getProducto().getNombre() + " x" + lc.getCantidad() + 
                      " ($" + lc.getPrecioUnitario() + " c/u) = $" + lc.getSubtotal());
        }
        
        IO.println("\nTotal: $" + getTotal());
    }
}