package modelo;

import java.io.Serializable;

public abstract class TipoCarga implements Serializable {
    private int numero;
    private String descricao;

    public TipoCarga(int numero, String descricao) {
        this.numero = numero;
        this.descricao = descricao;
    }

    public int getNumero() {
        return numero;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return String.format("%d;%s", numero, descricao);
    }
}
