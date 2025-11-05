package org.sakura.market.model;

import java.time.LocalDate;

public class AdminContenido extends Usuario{
    public AdminContenido(Long id, String nombre, String email, String password, LocalDate fechaRegistro, EstadoCuenta estadoCuenta) {
        super(id, nombre, email, password, fechaRegistro, estadoCuenta);
    }

    @Override
    public void iniciarSesion() {
        System.out.println("Administrador de contenido " + getNombre() + " ha iniciado sesión.");
    }

    @Override
    public void cambiarPass(String newPass) {
        this.setPassword(newPass);
        System.out.println("La contraseña del administrador de contenido ha sido cambiada exitosamente.");
    }

    public void publicarAnuncio(String contenido){
        System.out.println("Nuevo anuncio publicado: " + contenido);
    }

    public void eliminarAnuncio(String titulo){
        System.out.println("Anuncio '" + titulo + "' eliminado del sistema.");
    }

    public void revisarReportes(){
        System.out.println("Revisando reportes de contenido inapropiado...");
    }
}
