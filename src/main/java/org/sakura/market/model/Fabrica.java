package org.sakura.market.model;

import java.util.UUID;

public class Fabrica {
    private final UUID id;
    private String nombre;
    private String ubicacion;
    private int capacidadProduccion;

    public Fabrica(String nombre, String ubicacion, int capacidadProduccion) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.capacidadProduccion = capacidadProduccion;
    }

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public int getCapacidadProduccion() {
        return capacidadProduccion;
    }

    public void actualizarCapacidad(int nuevaCapacidad) {
        this.capacidadProduccion = nuevaCapacidad;
        System.out.println("Capacidad de producción actualizada a: " + nuevaCapacidad);
    }

    public void cambiarUbicacion(String nuevaUbicacion) {
        this.ubicacion = nuevaUbicacion;
        System.out.println("Ubicación de la fábrica actualizada a: " + nuevaUbicacion);
    }

    public void cambiarNombre(String nuevoNombre) {
        this.nombre = nuevoNombre;
        System.out.println("Nombre de la fábrica actualizado a: " + nuevoNombre);
    }

    public void iniciarProduccion() {
        System.out.println("Producción iniciada en la fábrica: " + nombre);
    }

    public void detenerProduccion() {
        System.out.println("Producción detenida en la fábrica: " + nombre);
    }

    public void mostrarInformacion() {
        System.out.println("Fábrica: " + nombre + " | Ubicación: " + ubicacion + " | Capacidad: " + capacidadProduccion);
    }
}
