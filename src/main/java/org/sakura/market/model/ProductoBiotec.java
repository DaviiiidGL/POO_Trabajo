package org.sakura.market.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductoBiotec extends Producto {
    private String patente;
    private LocalDate fechaCaducidad;
    private String efectoEspecial;
    private List<String> ingredienteActivo;
    private String restriccionesUso;

    public ProductoBiotec(long id, String nombre, String descripcion, BigDecimal precio, int stock, LocalDate fechaLanzamiento, String patente, LocalDate fechaCaducidad, String efectoEspecial, String restriccionesUso) {
        super(id, nombre, descripcion, precio, stock, fechaLanzamiento);
        this.patente = patente;
        this.fechaCaducidad = fechaCaducidad;
        this.efectoEspecial = efectoEspecial;
        this.ingredienteActivo = new ArrayList<>();
        this.restriccionesUso = restriccionesUso;
    }

    public void agregarIngrediente(String ingrediente) {
        ingredienteActivo.add(ingrediente);
        System.out.println("Ingrediente añadido: " + ingrediente);
    }

    public void mostrarIngredientes() {
        System.out.println("Ingredientes activos: " + ingredienteActivo);
    }

    public void verificarCaducidad() {
        if (LocalDate.now().isAfter(fechaCaducidad)) {
            System.out.println("El producto ha caducado.");
        } else {
            System.out.println("El producto sigue vigente.");
        }
    }

    public void aplicarEfectoEspecial() {
        System.out.println("Aplicando efecto especial: " + efectoEspecial);
    }

    public void mostrarRestricciones() {
        System.out.println("Restricciones de uso: " + restriccionesUso);
    }
}
