package models.shopping;

import utils.IO;

public class Direccion {
    private long id;
    private String calle;
    private String numero;
    private String ciudad;
    private String codigoPostal;

    public Direccion(long id, String calle, String numero, String ciudad, String codigoPostal) {
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
    }

    public long getId() { return id; }
    public String getCalle() { return calle; }
    public String getNumero() { return numero; }
    public String getCiudad() { return ciudad; }
    public String getCodigoPostal() { return codigoPostal; }

    public void mostrarInfo() {
        IO.println(calle + " " + numero + ", " + ciudad + " (CP: " + codigoPostal + ")");
    }
}