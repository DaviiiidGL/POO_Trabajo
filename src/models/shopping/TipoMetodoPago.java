package models.shopping;

public enum TipoMetodoPago {
    TARJETA_CREDITO("Tarjeta de Crédito"),
    DEBITO("Tarjeta de Débito"),
    EFECTIVO("Efectivo"),
    TRANSFERENCIA("Transferencia Bancaria"),
    PAYPAL("PayPal"),
    MERCADO_PAGO("Mercado Pago");

    private final String descripcion;

    TipoMetodoPago(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return this.descripcion;
    }
}