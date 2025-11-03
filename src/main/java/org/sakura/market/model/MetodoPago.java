package org.sakura.market.model;

public class MetodoPago {
    private Long id;
    private TipoMetodoPago tipo;
    private String titular;
    private String cvv;
    private String numero;
    private boolean permitido;

    public MetodoPago(Long id, TipoMetodoPago tipo, String titular, String cvv, String numero){
        this.id = id;
        this.tipo = tipo;
        this.titular = titular;
        this.cvv = cvv;
        this.numero = numero;
        this.permitido = false;
    }
}
