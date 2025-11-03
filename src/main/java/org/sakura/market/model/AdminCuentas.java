package org.sakura.market.model;

import java.time.LocalDate;

public class AdminCuentas extends Usuario{
    public AdminCuentas(Long id, String nombre, String email, String password, LocalDate fechaRegistro, EstadoCuenta estadoCuenta) {
        super(id, nombre, email, password, fechaRegistro, estadoCuenta);
    }

    @Override
    public void iniciarSesion() {

    }

    @Override
    public void cambiarPass(String newPass) {

    }
}
