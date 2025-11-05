package org.sakura.market.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Carrito {
    private final UUID id;
    private LocalDate fechaCreacion;
    private List<LineaCarrito> lineasCarrito;

    public Carrito() {
        this.id = UUID.randomUUID();
        this.fechaCreacion = LocalDate.now();
        this.lineasCarrito = new ArrayList<LineaCarrito>();
    }

    // GETTERS Y SETTERS

    public UUID getId() {
        return id;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public List<LineaCarrito> getLineasCarrito() {
        return lineasCarrito;
    }

    // PRODUCTOS
    public void agregarProducto(Producto producto, int cantidad) {
        if (!validarStock(producto, cantidad)) {
            System.out.println("No hay suficiente stock para el producto: " + producto.getNombre());
            return;
        }

        LineaCarrito existente = buscarProductoCarrito(producto);
        if (existente != null) {
            actualizarCantidad(producto, existente.getCantidad() + cantidad);
            System.out.println("Cantidad actualizada para el producto: " + producto.getNombre());
        } else {
            LineaCarrito nuevaLinea = new LineaCarrito(producto, cantidad);
            lineasCarrito.add(nuevaLinea);
            System.out.println("Producto agregado al carrito: " + producto.getNombre());
        }
    }

    public void eliminarProducto(Producto producto) {
        LineaCarrito linea = buscarProductoCarrito(producto);
        if (linea != null) {
            lineasCarrito.remove(linea);
            System.out.println("Producto eliminado del carrito: " + producto.getNombre());
        } else {
            System.out.println("El producto no se encontró en el carrito: " + producto.getNombre());
        }
    }

    public void actualizarCantidad(Producto producto, int cantidad) {
        LineaCarrito linea = buscarProductoCarrito(producto);
        if (linea != null) {
            lineasCarrito.remove(linea);
            lineasCarrito.add(new LineaCarrito(producto, cantidad));
            System.out.println("Cantidad actualizada para " + producto.getNombre() + ": " + cantidad);
        } else {
            System.out.println("No se encontró el producto en el carrito para actualizar.");
        }
    }

    public void vaciarCarrito() {
        lineasCarrito.clear();
        System.out.println("Carrito vaciado correctamente.");
    }

    // CONSULTAS

    public LineaCarrito buscarProductoCarrito(Producto producto) {
        for (LineaCarrito linea : lineasCarrito) {
            if (linea.getProducto().equals(producto)) {
                return linea;
            }
        }
        return null;
    }

    public boolean contieneProducto(Producto producto) {
        return buscarProductoCarrito(producto) != null;
    }

    public int cantidadProductosTotal() {
        int total = 0;
        for (LineaCarrito linea : lineasCarrito) {
            total += linea.getCantidad();
        }
        System.out.println("Cantidad total de productos en el carrito: " + total);
        return total;
    }

    // CALCULOS

    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (LineaCarrito linea : lineasCarrito) {
            total = total.add(linea.getSubtotal());
        }
        System.out.println("Total del carrito: $" + total);
        return total;
    }

    public BigDecimal calcularSubtotal(Producto producto) {
        LineaCarrito linea = buscarProductoCarrito(producto);
        if (linea != null) {
            System.out.println("Subtotal del producto " + producto.getNombre() + ": $" + linea.getSubtotal());
            return linea.getSubtotal();
        } else {
            System.out.println("El producto no está en el carrito.");
            return BigDecimal.ZERO;
        }
    }

    // VALIDACIONES

    public boolean estaVacio() {
        boolean vacio = lineasCarrito.isEmpty();
        System.out.println(vacio ? "El carrito está vacío." : "El carrito tiene productos.");
        return vacio;
    }

    public boolean validarStock(Producto producto, int cantidad) {
        boolean valido = producto.getStock() >= cantidad;
        if (!valido) {
            System.out.println("Stock insuficiente para el producto: " + producto.getNombre());
        }
        return valido;
    }
}
