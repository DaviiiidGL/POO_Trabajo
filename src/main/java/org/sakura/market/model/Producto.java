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

    public Producto(long id, String nombre, String descripcion, BigDecimal precio, int stock, LocalDate fechaLanzamiento) {
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

    public int getStock() {
        return stock;
    }

    public String getNombre() {
        return nombre;
    }

    public void habilitarProducto() {
        this.habilitado = true;
        System.out.println("Producto habilitado para la venta: " + nombre);
    }

    public void deshabilitarProducto() {
        this.habilitado = false;
        System.out.println("Producto deshabilitado: " + nombre);
    }

    public void actualizarPrecio(BigDecimal nuevoPrecio) {
        this.precio = nuevoPrecio;
        System.out.println("Precio actualizado a: $" + nuevoPrecio);
    }

    public void aumentarStock(int cantidad) {
        this.stock += cantidad;
        System.out.println("Stock aumentado. Nuevo stock: " + stock);
    }

    public void reducirStock(int cantidad) {
        if (cantidad <= stock) {
            this.stock -= cantidad;
            System.out.println("Stock reducido. Nuevo stock: " + stock);
        } else {
            System.out.println("No hay suficiente stock para reducir " + cantidad + " unidades.");
        }
    }

    public void mostrarInformacion() {
        System.out.println("Producto: " + nombre + " | Precio: $" + precio + " | Stock: " + stock + " | Habilitado: " + habilitado);
    }
}
