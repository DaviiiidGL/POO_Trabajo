package org.sakura.market.model;

import java.time.LocalDate;

public class Duena extends Usuario {

    public Duena(Long id, String nombre, String email, String password, LocalDate fechaRegistro, EstadoCuenta estadoCuenta) {
        super(id, nombre, email, password, fechaRegistro, estadoCuenta);
    }

    @Override
    public void iniciarSesion() {
        System.out.println("La dueña " + nombre + " ha iniciado sesión correctamente.");
    }

    @Override
    public void cambiarPass(String newPass) {
        this.password = newPass;
        System.out.println("La contraseña de la dueña " + nombre + " ha sido actualizada.");
    }

    public void supervisarOperaciones() {
        System.out.println("La dueña " + nombre + " está supervisando las operaciones del mercado.");
    }

    public void aprobarRegistroTrabajador() {
        System.out.println("La dueña " + nombre + " ha aprobado el registro de un nuevo trabajador.");
    }

    public void revisarHistorialCorporativo() {
        System.out.println("La dueña " + nombre + " está revisando el historial corporativo.");
    }

    public void generarReporteFinanciero() {
        System.out.println("La dueña " + nombre + " ha generado un reporte financiero.");
    }

    public void enviarComunicado() {
        System.out.println("La dueña " + nombre + " ha enviado un comunicado oficial a todos los empleados.");
    }
}
