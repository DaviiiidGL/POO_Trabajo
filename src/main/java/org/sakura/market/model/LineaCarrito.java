package org.sakura.market.model;

import java.math.BigDecimal;

public class LineaCarrito {
    private final Producto producto;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public LineaCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
        this.subtotal = calcularSubtotal();
    }

    private BigDecimal calcularSubtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void actualizarCantidad(int nuevaCantidad) {
        this.cantidad = nuevaCantidad;
        this.subtotal = calcularSubtotal();
        System.out.println("Cantidad actualizada: " + cantidad + " | Nuevo subtotal: $" + subtotal);
    }

    public void aplicarDescuento(BigDecimal porcentaje) {
        BigDecimal descuento = subtotal.multiply(porcentaje.divide(BigDecimal.valueOf(100)));
        BigDecimal totalConDescuento = subtotal.subtract(descuento);
        System.out.println("Descuento aplicado: " + porcentaje + "% | Total con descuento: $" + totalConDescuento);
    }

    public void mostrarDetalle() {
        System.out.println("Producto: " + producto + " | Cantidad: " + cantidad + " | Precio: $" + precioUnitario + " | Subtotal: $" + subtotal);
    }

    public void recalcularSubtotal() {
        this.subtotal = calcularSubtotal();
        System.out.println("Subtotal recalculado: $" + subtotal);
    }
}
