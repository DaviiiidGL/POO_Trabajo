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
}
