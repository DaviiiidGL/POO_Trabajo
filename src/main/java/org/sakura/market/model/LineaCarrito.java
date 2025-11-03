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

    private BigDecimal calcularSubtotal(){
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }


}
