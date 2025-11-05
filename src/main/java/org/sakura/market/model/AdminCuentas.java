package org.sakura.market.model;

import java.time.LocalDate;

public class AdminCuentas extends Usuario {
    public AdminCuentas(Long id, String nombre, String email, String password, LocalDate fechaRegistro, EstadoCuenta estadoCuenta) {
        super(id, nombre, email, password, fechaRegistro, estadoCuenta);
    }

    @Override
    public void iniciarSesion() {
        if (getEstadoCuenta() == EstadoCuenta.ACTIVO) {
            System.out.println("Inicio de sesión exitoso para el administrador: " + getNombre());
        } else {
            System.out.println("No se puede iniciar sesión. La cuenta del administrador " + getNombre() + " está " + getEstadoCuenta());
        }
    }

    @Override
    public void cambiarPass(String newPass) {
        if (newPass == null || newPass.isEmpty()) {
            System.out.println("La nueva contraseña no puede estar vacía.");
            return;
        }

        setPassword(newPass);
        System.out.println("Contraseña actualizada correctamente para el administrador: " + getNombre());
    }
}
