package org.sakura.market.model;

public class Direccion {
    private String calle;
    private String ciudad;
    private String codigoPostal;
    private String pais;

    public Direccion(String calle, String ciudad, String codigoPostal, String pais){
        this.calle = calle;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
    }
}
