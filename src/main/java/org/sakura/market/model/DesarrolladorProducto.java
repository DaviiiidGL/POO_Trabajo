package org.sakura.market.model;

import java.time.LocalDate;

public class DesarrolladorProducto extends Usuario {
    private String habilidad;

    public DesarrolladorProducto(long id, String nombre, String email, String password,
                                 LocalDate fechaRegistro, EstadoCuenta estadoCuenta, String habilidad) {
        super(id, nombre, email, password, fechaRegistro, estadoCuenta);
        this.habilidad = habilidad;
    }

    public String getHabilidad() {
        return habilidad;
    }

    public void setHabilidad(String habilidad) {
        this.habilidad = habilidad;
    }

    @Override
    public void iniciarSesion() {
        System.out.println("El desarrollador " + getNombre() + " ha iniciado sesión correctamente.");
    }

    @Override
    public void cambiarPass(String newPass) {
        setPassword(newPass);
        System.out.println("Contraseña actualizada exitosamente para el desarrollador " + getNombre());
    }

    public void desarrollarProducto(Producto producto) {
        System.out.println("El desarrollador " + getNombre() + " está trabajando en el producto: " + producto);
    }

    public void mostrarPerfil() {
        System.out.println("Desarrollador: " + getNombre() + " | Habilidad: " + habilidad +
                " | Estado: " + getEstadoCuenta());
    }
}
