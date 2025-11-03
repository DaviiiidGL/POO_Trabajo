package org.sakura.market.model;

import java.util.UUID;

public class Fabrica {
    private final UUID id;
    private String nombre;
    private String ubicacion;
    private int capacidadProduccion;

    public Fabrica(String nombre, String ubicacion, int capacidadProduccion){
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.capacidadProduccion = capacidadProduccion;
    }
}
