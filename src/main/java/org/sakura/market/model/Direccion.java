package org.sakura.market.model;

public class Direccion {
    private String calle;
    private String ciudad;
    private String codigoPostal;
    private String pais;

    public Direccion(String calle, String ciudad, String codigoPostal, String pais) {
        this.calle = calle;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
    }

    public String getCalle() {
        return calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getPais() {
        return pais;
    }

    public void actualizarCalle(String nuevaCalle) {
        this.calle = nuevaCalle;
        System.out.println("Calle actualizada a: " + nuevaCalle);
    }

    public void actualizarCiudad(String nuevaCiudad) {
        this.ciudad = nuevaCiudad;
        System.out.println("Ciudad actualizada a: " + nuevaCiudad);
    }

    public void actualizarCodigoPostal(String nuevoCodigo) {
        this.codigoPostal = nuevoCodigo;
        System.out.println("Código postal actualizado a: " + nuevoCodigo);
    }

    public void actualizarPais(String nuevoPais) {
        this.pais = nuevoPais;
        System.out.println("País actualizado a: " + nuevoPais);
    }

    public void mostrarDireccionCompleta() {
        System.out.println("Dirección: " + calle + ", " + ciudad + ", " + codigoPostal + ", " + pais);
    }

    public boolean perteneceAlPais(String paisComparar) {
        return this.pais.equalsIgnoreCase(paisComparar);
    }
}
