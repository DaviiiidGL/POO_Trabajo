package org.sakura.market.model;

import java.time.LocalDate;

public class Duena extends Usuario{

    public Duena(Long id, String nombre, String email, String password, LocalDate fechaRegistro, EstadoCuenta estadoCuenta) {
        super(id, nombre, email, password, fechaRegistro, estadoCuenta);
    }

    @Override
    public void iniciarSesion() {

    }

    @Override
    public void cambiarPass(String newPass) {

    }
}
