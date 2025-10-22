package org.sakura.market.model;

import java.time.LocalDate;

public abstract class Usuario {
    protected Long id;
    protected String nombre;
    protected String email;
    protected String password;
    protected LocalDate fechaRegistro;
    protected String estadoCuenta;

    public Usuario(Long id, String nombre, String email, String password, LocalDate fechaRegistro, String estadoCuenta) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.fechaRegistro = fechaRegistro;
        this.estadoCuenta = estadoCuenta;
    }

    public abstract void iniciarSesion();

    public abstract void cambiarPass(String newPass);

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public void setPassword(String password) {this.password = password;}

    public LocalDate getFechaRegistro() {return fechaRegistro;}

    public String getEstadoCuenta() {return estadoCuenta;}
    public void setEstadoCuenta(String estadoCuenta) {this.estadoCuenta = estadoCuenta;}

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
