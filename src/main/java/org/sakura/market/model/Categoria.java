package org.sakura.market.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Categoria {
    private UUID id;
    private String nombre;
    private String descripcion;
    private List<Producto> productos;

    public Categoria(String nombre, String descripcion){
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = new ArrayList<>();
    }

    // GETTERS Y SETTERS

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    // GESTION PRODUCTOS

    public void agregarProducto(Producto producto){
        productos.add(producto);
        System.out.println("Producto agregado a la categoría '" + nombre + "': " + producto.getNombre());
    }

    public void eliminarProducto(Producto producto){
        if (productos.remove(producto)) {
            System.out.println("Producto eliminado de la categoría '" + nombre + "': " + producto.getNombre());
        } else {
            System.out.println("El producto no se encontró en la categoría '" + nombre + "'.");
        }
    }

    public void actualizarDescripcion(String newDescripcion){
        this.descripcion = newDescripcion;
        System.out.println("Descripción actualizada de la categoría '" + nombre + "': " + descripcion);
    }

    // CONSULTAS

    public void listarProductos(){
        System.out.println("Productos en la categoría '" + nombre + "':");
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados en esta categoría.");
        } else {
            for (Producto p : productos) {
                System.out.println("- " + p.getNombre() + " | Precio: $" + p.getPrecio() + " | Stock: " + p.getStock());
            }
        }
    }

    public void mostrarInfoCategoria() {
        System.out.println("Categoría: " + nombre);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Total de productos: " + productos.size());
    }
}
