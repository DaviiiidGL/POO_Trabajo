package models.products;

import utils.IO;
import exceptions.ValidacionException;
import exceptions.StockException;
import java.time.LocalDate;

public class Producto {
    private long id;
    private String nombre, descripcion;
    private double precio;
    private int stock;
    private boolean habilitado;
    private Categoria categoria;
    private LocalDate fechaCreacion;

    public Producto(long id, String nombre, String descripcion, double precio, int stock, LocalDate fecha, Categoria categoria) {
        if (nombre == null || nombre.isBlank()) 
            throw new ValidacionException("El nombre del producto es obligatorio.");
        if (precio <= 0) 
            throw new ValidacionException("El precio debe ser mayor que cero.");
        if (stock < 0) 
            throw new ValidacionException("El stock no puede ser negativo.");
        
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion != null ? descripcion : "";
        this.precio = precio;
        this.stock = stock;
        this.habilitado = true;
        this.categoria = categoria;
        this.fechaCreacion = fecha != null ? fecha : LocalDate.now();
    }

    public long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public boolean isHabilitado() { return habilitado; }
    public Categoria getCategoria() { return categoria; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) 
            throw new ValidacionException("El nombre no puede estar vacío.");
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion != null ? descripcion : "";
    }

    public void setPrecio(double precio) {
        if (precio <= 0) 
            throw new ValidacionException("El precio debe ser mayor que cero.");
        this.precio = precio;
    }

    public void setStock(int stock) {
        if (stock < 0) 
            throw new ValidacionException("El stock no puede ser negativo.");
        this.stock = stock;
    }

    public void reducirStock(int cantidad) {
        if (cantidad <= 0) 
            throw new ValidacionException("La cantidad a reducir debe ser positiva.");
        if (cantidad > stock) 
            throw new StockException("Stock insuficiente. Stock actual: " + stock);
        this.stock -= cantidad;
    }

    public void aumentarStock(int cantidad) {
        if (cantidad <= 0) 
            throw new ValidacionException("La cantidad a aumentar debe ser positiva.");
        this.stock += cantidad;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void habilitar() { this.habilitado = true; }
    public void deshabilitar() { this.habilitado = false; }

    public void mostrarInfo() {
        IO.println("=== " + nombre + " ===");
        IO.println("Precio: $" + precio);
        IO.println("Stock disponible: " + stock);
        IO.println("Descripción: " + descripcion);
        IO.println("Estado: " + (habilitado ? "Disponible" : "No disponible"));
        IO.println("Categoría: " + categoria);
        IO.println("ID: " + id);
    }
}