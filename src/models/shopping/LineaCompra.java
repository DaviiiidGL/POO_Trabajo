package models.shopping;

import models.products.Producto;

public class LineaCompra {
    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public LineaCompra(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
        producto.reducirStock(cantidad);
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getSubtotal() { return precioUnitario * cantidad; }
}