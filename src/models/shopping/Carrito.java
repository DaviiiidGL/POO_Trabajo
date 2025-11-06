package models.shopping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.products.Producto;
import models.users.Cliente;
import exceptions.ValidacionException;
import exceptions.StockException;
import utils.IO;

public class Carrito {
    private long id;
    private LocalDate fechaCreacion;
    private Cliente cliente;
    private List<LineaCarrito> lineasCarrito;

    public Carrito(long id, LocalDate fechaCreacion, Cliente cliente) {
        if (fechaCreacion == null) 
            throw new ValidacionException("La fecha de creación es obligatoria.");
        if (cliente == null) 
            throw new ValidacionException("El carrito debe pertenecer a un cliente.");
        
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.cliente = cliente;
        this.lineasCarrito = new ArrayList<>();
    }

    public long getId() { return id; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public Cliente getCliente() { return cliente; }
    public List<LineaCarrito> getLineasCarrito() { return new ArrayList<>(lineasCarrito); }
    
    public double getTotal() {
        return lineasCarrito.stream()
                .mapToDouble(LineaCarrito::getSubtotal)
                .sum();
    }

    public void agregarProducto(Producto producto, int cantidad) {
        if (producto == null) 
            throw new ValidacionException("El producto no puede ser nulo.");
        if (cantidad <= 0) 
            throw new ValidacionException("La cantidad debe ser positiva.");
        if (!producto.isHabilitado())
            throw new ValidacionException("El producto no está disponible para la venta.");
        if (cantidad > producto.getStock())
            throw new StockException("No hay suficiente stock. Disponible: " + producto.getStock());

        // Buscar si el producto ya está en el carrito
        for (LineaCarrito lc : lineasCarrito) {
            if (lc.getProducto().equals(producto)) {
                try {
                    lc.aumentarCantidad(cantidad);
                    IO.println("Cantidad actualizada en el carrito.");
                    return;
                } catch (StockException e) {
                    throw new StockException("No se puede agregar más unidades. " + e.getMessage());
                }
            }
        }

        // Si no está en el carrito, crear nueva línea
        lineasCarrito.add(new LineaCarrito(producto, cantidad));
        IO.println("Producto agregado al carrito.");
    }

    public void eliminarProducto(Producto producto) {
        if (producto == null)
            throw new ValidacionException("El producto no puede ser nulo.");

        boolean removido = lineasCarrito.removeIf(lc -> lc.getProducto().equals(producto));
        if (removido) {
            IO.println("Producto eliminado del carrito.");
        } else {
            IO.println("Producto no encontrado en el carrito.");
        }
    }

    public void actualizarCantidad(Producto producto, int nuevaCantidad) {
        if (producto == null)
            throw new ValidacionException("El producto no puede ser nulo.");
        if (nuevaCantidad <= 0)
            throw new ValidacionException("La cantidad debe ser positiva.");

        for (LineaCarrito lc : lineasCarrito) {
            if (lc.getProducto().equals(producto)) {
                lc.actualizarCantidad(nuevaCantidad);
                IO.println("Cantidad actualizada.");
                return;
            }
        }
        throw new ValidacionException("Producto no encontrado en el carrito.");
    }

    public void vaciarCarrito() {
        lineasCarrito.clear();
        IO.println("Carrito vaciado.");
    }

    public void mostrarInfo() {
        if (lineasCarrito.isEmpty()) {
            IO.println("El carrito está vacío.");
            return;
        }

        IO.println("=== Carrito de Compras ===");
        IO.println("Cliente: " + cliente.getNombre());
        IO.println("Fecha de creación: " + fechaCreacion);
        IO.println("\nProductos en el carrito:");
        
        for (LineaCarrito lc : lineasCarrito) {
            IO.println("-----------------------");
            IO.println(lc.getProducto().getNombre());
            IO.println("Cantidad: " + lc.getCantidad());
            IO.println("Precio unitario: $" + lc.getPrecioUnitario());
            IO.println("Subtotal: $" + lc.getSubtotal());
        }
        
        IO.println("=======================");
        IO.println("Total del carrito: $" + getTotal());
    }
}