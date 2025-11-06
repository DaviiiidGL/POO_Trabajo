package models.shopping;

import utils.IO;

public class MetodoPago {
    private long id;
    private TipoMetodoPago tipo;
    private String numeroOIdentificador;

    public MetodoPago(long id, TipoMetodoPago tipo, String numeroOIdentificador) {
        this.id = id;
        this.tipo = tipo;
        this.numeroOIdentificador = numeroOIdentificador;
    }

    public long getId() { return id; }
    public TipoMetodoPago getTipo() { return tipo; }
    public String getNumeroOIdentificador() { return numeroOIdentificador; }

    public void mostrarInfo() {
        IO.println(tipo + ": " + numeroOIdentificador);
    }
}