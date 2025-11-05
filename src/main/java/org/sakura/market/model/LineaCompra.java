package org.sakura.market.model;

public class LineaCompra {
    private final int cantidad;
    private final double precioUnitario;
    private final double subtotal;
    private final Producto producto;

    public LineaCompra(Producto producto, int cantidad, double precioUnitario, double subtotal) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double calcularSubtotal() {
        return cantidad * precioUnitario;
    }

    public void mostrarDetalle() {
        System.out.println("Producto: " + producto + " | Cantidad: " + cantidad + " | Precio unitario: $" + precioUnitario + " | Subtotal: $" + subtotal);
    }

    public boolean validarCantidadDisponible() {
        if (producto.getStock() >= cantidad) {
            System.out.println("Stock disponible para el producto seleccionado.");
            return true;
        } else {
            System.out.println("Stock insuficiente para el producto seleccionado.");
            return false;
        }
    }

    public void aplicarDescuento(double porcentaje) {
        double descuento = subtotal * (porcentaje / 100);
        double totalConDescuento = subtotal - descuento;
        System.out.println("Descuento aplicado: " + porcentaje + "% | Total con descuento: $" + totalConDescuento);
    }
}
