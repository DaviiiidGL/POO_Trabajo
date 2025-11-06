package models.products;

import java.time.LocalDate;
import utils.IO;

public class ProductoBiotec extends Producto {
    private String componenteActivo;

    public ProductoBiotec(long id, String nombre, String descripcion, double precio, int stock, 
                         LocalDate fecha, Categoria categoria, String componenteActivo) {
        super(id, nombre, descripcion, precio, stock, fecha, categoria);
        this.componenteActivo = componenteActivo;
    }

    public String getComponenteActivo() {
        return componenteActivo;
    }

    public void setComponenteActivo(String componenteActivo) {
        this.componenteActivo = componenteActivo;
    }

    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
        IO.println("Componente activo: " + componenteActivo);
    }
}