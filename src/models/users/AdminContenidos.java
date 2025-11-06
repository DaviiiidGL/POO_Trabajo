package models.users;

import models.products.Producto;
import java.time.LocalDate;
import utils.IO;

public class AdminContenidos extends Usuario {
    private LocalDate fechaContratacion;

    public AdminContenidos(long id, String nombre, String email, String password, LocalDate fechaContratacion) {
        super(id, nombre, email, password, RolUsuario.ADMIN_CONTENIDOS);
        this.fechaContratacion = fechaContratacion;
    }

    public void editarProducto(Producto producto, String nombre, String descripcion, double precio, int stock) {
        if (producto == null) throw new IllegalArgumentException("Producto no puede ser nulo");
        if (nombre != null && !nombre.isBlank()) producto.setNombre(nombre);
        if (descripcion != null) producto.setDescripcion(descripcion);
        if (precio > 0) producto.setPrecio(precio);
        if (stock >= 0) producto.setStock(stock);
        IO.println("Producto actualizado correctamente.");
    }

    public void habilitarProducto(Producto producto) {
        if (producto == null) throw new IllegalArgumentException("Producto no puede ser nulo");
        producto.habilitar();
        IO.println("Producto habilitado.");
    }

    public void deshabilitarProducto(Producto producto) {
        if (producto == null) throw new IllegalArgumentException("Producto no puede ser nulo");
        producto.deshabilitar();
        IO.println("Producto deshabilitado.");
    }

    @Override
    public void mostrarInfo() {

    }
}