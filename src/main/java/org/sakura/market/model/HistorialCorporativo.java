package org.sakura.market.model;

import java.time.LocalDate;
import java.util.UUID;

public class HistorialCorporativo {
    private final UUID id;
    private LocalDate fecha;
    private String descripcion;
    private String autor;

    public HistorialCorporativo(String descripcion, String autor){
        this.id = UUID.randomUUID();
        this.fecha = LocalDate.now();
        this.descripcion = descripcion;
        this.autor = autor;
    }

    public UUID getId() {return id;}

    public LocalDate getFecha(){ return fecha;}

    public String getDescripcion(){return descripcion;}

    public String getAutor(){return autor;}

    public boolean esReciente(int dias){
        LocalDate fechaLimite = LocalDate.now().minusDays(dias);
        return fecha.isAfter(fechaLimite) || fecha.isEqual(fechaLimite);
    }

    public String resumen(){
        return fecha.toString() + ": " + descripcion + " (Por: " + autor +")";
    }

    @Override
    public String toString() {
        return resumen();
    }
}
