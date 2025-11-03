package org.sakura.market.model;

import java.time.LocalDate;

public class DesarrolladorProducto extends Usuario{
    private String habilidad;

    public DesarrolladorProducto(long id, String nombre, String email, String password, LocalDate fechaRegistro, EstadoCuenta estadoCuenta, String habilidad){
        super(id, nombre, email, password, fechaRegistro, estadoCuenta);
        this.habilidad = habilidad;
    }

    @Override
    public void iniciarSesion() {

    }

    @Override
    public void cambiarPass(String newPass) {

    }
}
