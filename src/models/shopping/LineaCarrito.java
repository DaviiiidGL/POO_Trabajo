package models.shopping;

import models.products.Producto;
import exceptions.ValidacionException;
import exceptions.StockException;
import utils.IO;

public class LineaCarrito {
    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public LineaCarrito(Producto producto, int cantidad) {
        if (producto == null) 
            throw new ValidacionException("El producto no puede ser nulo.");
        if (cantidad <= 0) 
            throw new ValidacionException("La cantidad debe ser positiva.");
        if (cantidad > producto.getStock()) 
            throw new StockException("No hay suficiente stock. Disponible: " + producto.getStock());
        if (!producto.isHabilitado()) 
            throw new ValidacionException("El producto no está disponible para la venta.");

        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getSubtotal() { return precioUnitario * cantidad; }

    public void aumentarCantidad(int delta) {
        if (delta <= 0) 
            throw new ValidacionException("El incremento debe ser positivo.");
        if (cantidad + delta > producto.getStock()) 
            throw new StockException("No hay suficiente stock. Disponible: " + producto.getStock());
        cantidad += delta;
    }

    public void actualizarCantidad(int nuevaCantidad) {
        if (nuevaCantidad <= 0) 
            throw new ValidacionException("La cantidad debe ser positiva.");
        if (nuevaCantidad > producto.getStock()) 
            throw new StockException("No hay suficiente stock. Disponible: " + producto.getStock());
        this.cantidad = nuevaCantidad;
    }

    public void mostrarInfo() {
        IO.println(producto.getNombre() + " x" + cantidad);
        IO.println("Precio unitario: $" + precioUnitario);
        IO.println("Subtotal: $" + getSubtotal());
    }
}