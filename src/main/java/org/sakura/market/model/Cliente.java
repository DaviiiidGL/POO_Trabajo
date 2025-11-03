package org.sakura.market.model;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;


public class Cliente extends Usuario {
    private List<Direccion> direcciones;
    private String telefono;
    private Carrito carrito;
    private List<Compra> historialCompras;

    public Cliente(Long id, String nombre,String email, String password,LocalDate fechaRegistro, String estadoCuenta, String telefono){
        super(id, nombre, email, password, fechaRegistro, EstadoCuenta.valueOf(estadoCuenta));
        this.telefono = telefono;
        this.carrito = new Carrito();
        this.historialCompras = new ArrayList<>();
    }

    @Override
    public void iniciarSesion(){

    }

    @Override
    public void cambiarPass(String newPass){
        this.setPassword(newPass);
    }
}
