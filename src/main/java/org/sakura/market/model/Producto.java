package org.sakura.market.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Producto {
    private final long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int stock;
    private LocalDate fechaLanzamiento;
    private boolean habilitado;

    public Producto(long id, String nombre, String descripcion, BigDecimal precio, int stock, LocalDate fechaLanzamiento){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.fechaLanzamiento = fechaLanzamiento;
        this.habilitado = false;
    }


    public BigDecimal getPrecio() {
        return precio;
    }

    public int getStock(){
        return stock;
    }
}
