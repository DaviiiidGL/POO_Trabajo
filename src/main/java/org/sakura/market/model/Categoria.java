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

    // GESTION PRODUCTOS

    public void agregarProducto(Producto producto){
        // Agrega Productos a la Categoria
    }

    public void eliminarProducto(Producto producto){
        // Elimina Producto de la Categoria
    }

    public void actualizarDescripcion(String newDescripcion){
        this.descripcion = newDescripcion;
    }

    // CONSULTAS

}
