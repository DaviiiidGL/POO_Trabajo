package models.users;

import models.shopping.*;
import utils.IO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private String telefono;
    private List<MetodoPago> metodosPago;
    private List<Direccion> direcciones;
    private Carrito carrito;
    private List<Compra> historial;

    public Cliente(long id, String nombre, String email, String password, LocalDate date, String telefono) {
        super(id, nombre, email, password, RolUsuario.CLIENTE);
        this.telefono = telefono;
        this.metodosPago = new ArrayList<>();
        this.direcciones = new ArrayList<>();
        this.carrito = new Carrito(id, LocalDate.now(), this);
        this.historial = new ArrayList<>();
    }

    public String getTelefono() { return telefono; }
    public List<MetodoPago> getMetodosPago() { return new ArrayList<>(metodosPago); }
    public List<Direccion> getDirecciones() { return new ArrayList<>(direcciones); }
    public Carrito getCarrito() { return carrito; }
    public List<Compra> getHistorial() { return new ArrayList<>(historial); }

    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public void agregarMetodoDePago(MetodoPago metodoPago) {
        metodosPago.add(metodoPago);
    }

    public void agregarDireccion(Direccion direccion) {
        direcciones.add(direccion);
    }

    public Compra comprarCarrito() {
        if (carrito.getLineasCarrito().isEmpty()) {
            throw new IllegalStateException("El carrito está vacío");
        }
        if (metodosPago.isEmpty()) {
            throw new IllegalStateException("Debe agregar al menos un método de pago");
        }
        if (direcciones.isEmpty()) {
            throw new IllegalStateException("Debe agregar al menos una dirección de envío");
        }

        Compra compra = new Compra(System.currentTimeMillis(), LocalDate.now(), this);
        compra.setMetodoPago(metodosPago.get(0));
        compra.setDireccionEnvio(direcciones.get(0));

        for (LineaCarrito lc : carrito.getLineasCarrito()) {
            compra.agregarLineaCompra(new LineaCompra(lc.getProducto(), lc.getCantidad()));
        }

        carrito.vaciarCarrito();
        historial.add(compra);
        return compra;
    }

    @Override
    public void mostrarInfo() {
        IO.println("\n=== Cliente: " + nombre + " ===");
        IO.println("Email: " + email);
        IO.println("Teléfono: " + telefono);

        if (!metodosPago.isEmpty()) {
            IO.println("\nMétodos de pago:");
            for (MetodoPago mp : metodosPago) {
                IO.print("- ");
                mp.mostrarInfo();
            }
        } else {
            IO.println("\nNo hay métodos de pago registrados.");
        }

        if (!direcciones.isEmpty()) {
            IO.println("\nDirecciones:");
            for (Direccion d : direcciones) {
                IO.print("- ");
                d.mostrarInfo();
            }
        } else {
            IO.println("\nNo hay direcciones registradas.");
        }
    }
}