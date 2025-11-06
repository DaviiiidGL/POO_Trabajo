package models.users;

import java.time.LocalDate;
import utils.IO;

public class AdminCuentas extends Usuario {
    private LocalDate fechaContratacion;

    public AdminCuentas(long id, String nombre, String email, String password, LocalDate fechaContratacion) {
        super(id, nombre, email, password, RolUsuario.ADMIN_CUENTAS);
        this.fechaContratacion = fechaContratacion;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void editarCliente(Cliente cliente, String nuevoNombre, String nuevoEmail, String nuevoTelefono) {
        if (cliente == null) throw new IllegalArgumentException("Cliente no puede ser nulo");
        if (nuevoNombre != null && !nuevoNombre.isBlank()) cliente.setNombre(nuevoNombre);
        if (nuevoEmail != null && !nuevoEmail.isBlank()) cliente.setEmail(nuevoEmail);
        if (nuevoTelefono != null && !nuevoTelefono.isBlank()) cliente.setTelefono(nuevoTelefono);
        IO.println("Cliente actualizado correctamente.");
    }

    public void suspenderCliente(Cliente cliente) {
        // Implementación de la suspensión de cliente
        IO.println("Cliente suspendido.");
    }

    public void activarCliente(Cliente cliente) {
        // Implementación de la activación de cliente
        IO.println("Cliente activado.");
    }

    public void eliminarCliente(Cliente cliente) {
        // Implementación de la eliminación de cliente
        IO.println("Cliente eliminado.");
    }

    @Override
    public void mostrarInfo() {

    }
}