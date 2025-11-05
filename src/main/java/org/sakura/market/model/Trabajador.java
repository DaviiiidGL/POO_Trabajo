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

    public void actualizarSalud(String nuevaCondicion) {
        this.condicionSalud = nuevaCondicion;
        System.out.println("Condición de salud actualizada a: " + nuevaCondicion);
    }

    public void registrarLlegada(LocalDate nuevaFecha) {
        this.fechaLlegada = nuevaFecha;
        System.out.println("Fecha de llegada registrada: " + nuevaFecha);
    }

    public void mostrarInformacion() {
        System.out.println("Trabajador de " + PaisOrigen + ", edad " + edad + ", condición: " + condicionSalud);
    }

    public void cumplirAnios() {
        this.edad++;
        System.out.println("El trabajador ha cumplido años. Nueva edad: " + edad);
    }
}
