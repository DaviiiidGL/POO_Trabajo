package org.sakura.market.model;

import java.time.LocalDate;
import java.util.UUID;

public class Trabajador {
    private final UUID id;
    private final String PaisOrigen;
    private LocalDate fechaLlegada;
    private int edad;
    private String condicionSalud;

    public Trabajador(String paisOrigen, LocalDate fechaLlegada, int edad, String condicionSalud) {
        this.id = UUID.randomUUID();
        PaisOrigen = paisOrigen;
        this.fechaLlegada = fechaLlegada;
        this.edad = edad;
        this.condicionSalud = condicionSalud;
    }
}
