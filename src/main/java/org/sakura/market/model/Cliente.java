package org.sakura.market.model;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class Cliente extends Usuario {
    private List<Direccion> direcciones;
    private String telefono;
    private Carrito carrito;
    private List<Compra> historialCompras;

    public Cliente(Long id, String nombre, String email, String password, LocalDate fechaRegistro, String estadoCuenta, String telefono) {
        super(id, nombre, email, password, fechaRegistro, EstadoCuenta.valueOf(estadoCuenta));
        this.telefono = telefono;
        this.carrito = new Carrito();
        this.historialCompras = new ArrayList<>();
    }

    @Override
    public void iniciarSesion() {
        System.out.println("Cliente " + nombre + " ha iniciado sesión exitosamente.");
    }

    @Override
    public void cambiarPass(String newPass) {
        this.setPassword(newPass);
        System.out.println("Contraseña actualizada correctamente para el cliente " + nombre);
    }

    public void agregarDireccion(Direccion direccion) {
        if (direcciones == null) {
            direcciones = new ArrayList<>();
        }
        direcciones.add(direccion);
        System.out.println("Dirección agregada correctamente para el cliente " + nombre);
    }

    public void agregarAlCarrito(Producto producto, int cantidad) {
        carrito.agregarProducto(producto, cantidad);
        System.out.println("Producto agregado al carrito: " + producto.getNombre());
    }

    public void vaciarCarrito() {
        carrito.vaciarCarrito();
        System.out.println("El carrito del cliente " + nombre + " ha sido vaciado.");
    }

    public void realizarCompra() {
        Compra compra = new Compra(LocalDate.now(), null, EstadoCompra.PENDIENTE, null);
        historialCompras.add(compra);
        System.out.println("Compra registrada exitosamente para el cliente " + nombre);
    }

    public void verHistorialCompras() {
        System.out.println("Historial de compras del cliente " + nombre + ":");
        if (historialCompras.isEmpty()) {
            System.out.println("No existen compras registradas.");
        } else {
            for (Compra compra : historialCompras) {
                compra.mostrarDetalleCompra();
            }
        }
    }
}
