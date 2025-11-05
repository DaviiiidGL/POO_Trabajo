package org.sakura.market.model;

import java.time.LocalDate;
import java.util.UUID;

public class Consejo {
    private final UUID id;
    private String autor;
    private String contenido;
    private LocalDate fecha;

    public Consejo(String autor, String contenido) {
        this.id = UUID.randomUUID();
        this.autor = autor;
        this.contenido = contenido;
        this.fecha = LocalDate.now();
    }

    public void mostrarConsejo() {
        System.out.println("[" + fecha + "] Consejo de " + autor + ": " + contenido);
    }

    public void editarConsejo(String nuevoContenido) {
        this.contenido = nuevoContenido;
        this.fecha = LocalDate.now();
        System.out.println("Consejo actualizado: " + contenido);
    }

    public void eliminarConsejo() {
        System.out.println("Consejo de " + autor + " eliminado del registro.");
    }

    public void mostrarResumen() {
        System.out.println("Autor: " + autor + " | Fecha: " + fecha);
    }
}
