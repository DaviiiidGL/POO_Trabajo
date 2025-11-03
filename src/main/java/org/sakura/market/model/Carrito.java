package org.sakura.market.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Carrito {
    private final UUID id;
    private LocalDate fechaCreacion;
    private List<LineaCarrito> lineasCarrito;

    public Carrito(){
        this.id = UUID.randomUUID();
        this.fechaCreacion = LocalDate.now();
        this.lineasCarrito = new ArrayList<LineaCarrito>();
    }

    // GETTERS Y SETTERS

    // PRODUCTOS
    public void agregarProducto(Producto producto, int cantidad){
        // Deberia añadir un producto al carrito
    }

    public void eliminarProducto(Producto producto){
        // Deberia eliminar un producto del carrito
    }

    public void actualizarCantidad(Producto producto, int cantidad){
        // Un producto existente se le cambia la cantidad
    }

    public void vaciarCarrito(){
        lineasCarrito.clear();
    }

    // CONSULTAS

    public LineaCarrito buscarProductoCarrito(Producto producto){
        // Deberia de devolver la linea en la que se encuentra el producto

        return null;
    }

    public boolean contieneProducto(Producto producto){
        // Devuelve si un producto ya se encuentra dentro del carrito
        return true;
    }

    public int cantidadProductosTotal(){
        // Devuelve cantidad total de productos en el carrito
        return 0;
    }

    // CALCULOS

    public BigDecimal calcularTotal(){
        // Devuelve valor total de la compra
        BigDecimal total = BigDecimal.ZERO;
        return total;
    }

    public BigDecimal calcularSubtotal(Producto producto){
        // Devuelve el valor total del producto dado
        return BigDecimal.ONE;
    }

    // VALIDACIONES

    public boolean estaVacio(){
        return lineasCarrito.isEmpty();
    }

    public boolean validarStock(Producto producto, int cantidad){
        return producto.getStock() >= cantidad;
    }
}
