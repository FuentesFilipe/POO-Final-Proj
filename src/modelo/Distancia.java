package modelo;

import java.io.Serializable;

public class Distancia implements Serializable {
    private int origem;
    private int destino;
    private double valor;

    public Distancia(int origem, int destino, double valor) {
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
    }

    public int getOrigem() {
        return origem;
    }

    public int getDestino() {
        return destino;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return String.format("%d;%d;%.2f", origem, destino, valor);
    }
}
