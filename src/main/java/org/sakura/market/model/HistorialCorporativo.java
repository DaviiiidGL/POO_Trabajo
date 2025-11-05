package org.sakura.market.model;

import java.time.LocalDate;
import java.util.UUID;

public class HistorialCorporativo {
    private final UUID id;
    private LocalDate fecha;
    private String descripcion;
    private String autor;

    public HistorialCorporativo(String descripcion, String autor) {
        this.id = UUID.randomUUID();
        this.fecha = LocalDate.now();
        this.descripcion = descripcion;
        this.autor = autor;
    }

    public UUID getId() { return id; }

    public LocalDate getFecha() { return fecha; }

    public String getDescripcion() { return descripcion; }

    public String getAutor() { return autor; }

    public boolean esReciente(int dias) {
        LocalDate fechaLimite = LocalDate.now().minusDays(dias);
        return fecha.isAfter(fechaLimite) || fecha.isEqual(fechaLimite);
    }

    public String resumen() {
        return fecha.toString() + ": " + descripcion + " (Por: " + autor + ")";
    }

    @Override
    public String toString() {
        return resumen();
    }

    public void actualizarDescripcion(String nuevaDescripcion) {
        this.descripcion = nuevaDescripcion;
        System.out.println("Descripción del historial actualizada a: " + nuevaDescripcion);
    }

    public void cambiarAutor(String nuevoAutor) {
        this.autor = nuevoAutor;
        System.out.println("Autor actualizado a: " + nuevoAutor);
    }

    public void mostrarDetalles() {
        System.out.println("Historial ID: " + id + " | Fecha: " + fecha + " | Descripción: " + descripcion + " | Autor: " + autor);
    }

    public void registrarNuevaFecha(LocalDate nuevaFecha) {
        this.fecha = nuevaFecha;
        System.out.println("Fecha del historial actualizada a: " + nuevaFecha);
    }
}
