package models.products;

public enum Categoria {
    FACIAL("Productos para el cuidado facial"),
    CORPORAL("Productos para el cuidado corporal"),
    CABELLO("Productos para el cuidado del cabello"),
    MAQUILLAJE("Productos de maquillaje"),
    FRAGANCIAS("Perfumes y fragancias"),
    BIOTEC("Productos biotecnológicos");

    private final String descripcion;

    Categoria(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return this.name() + " - " + this.descripcion;
    }
}