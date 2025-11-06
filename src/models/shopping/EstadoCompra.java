package models.shopping;

public enum EstadoCompra {
    PENDIENTE("Pendiente de procesar"),
    EN_PROCESO("En proceso de preparación"),
    EN_CAMINO("En camino"),
    ENTREGADO("Entregado"),
    CANCELADO("Cancelado");

    private final String descripcion;

    EstadoCompra(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return this.name() + " (" + this.descripcion + ")";
    }
}