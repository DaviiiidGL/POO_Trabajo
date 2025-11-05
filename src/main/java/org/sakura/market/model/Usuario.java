package org.sakura.market.model;

import java.time.LocalDate;

public abstract class Usuario {
    protected Long id;
    protected String nombre;
    protected String email;
    protected String password;
    protected LocalDate fechaRegistro;
    protected EstadoCuenta estadoCuenta;

    public Usuario(Long id, String nombre, String email, String password, LocalDate fechaRegistro, EstadoCuenta estadoCuenta) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.fechaRegistro = fechaRegistro;
        this.estadoCuenta = estadoCuenta;
    }

    public abstract void iniciarSesion();

    public abstract void cambiarPass(String newPass);

    public void cerrarSesion() {
        System.out.println(nombre + " ha cerrado sesión.");
    }

    public void actualizarEstadoCuenta(EstadoCuenta nuevoEstado) {
        this.estadoCuenta = nuevoEstado;
        System.out.println("El estado de la cuenta de " + nombre + " se actualizó a: " + nuevoEstado);
    }

    public void mostrarInformacion() {
        System.out.println("Usuario: " + nombre + " | Email: " + email + " | Estado: " + estadoCuenta);
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public void setPassword(String password) {this.password = password;}

    public LocalDate getFechaRegistro() {return fechaRegistro;}

    public EstadoCuenta getEstadoCuenta() {return estadoCuenta;}
    public void setEstadoCuenta(EstadoCuenta estadoCuenta) {this.estadoCuenta = estadoCuenta;}

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", estadoCuenta='" + estadoCuenta + '\'' +
                '}';
    }
}
