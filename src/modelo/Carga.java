package modelo;

import enums.Situacao;
import tipocarga.*;

public class Carga {
    private int identificador;
    private int peso;
    private double valorDeclarado;
    private int tempoMaximo;
    private Cliente cliente;
    private Porto origem;
    private Porto destino;
    private TipoCarga tipoCarga;
    private Situacao situacao;

    public Carga(
            int identificador, int peso, double valorDeclarado, int tempoMaximo,
            Cliente cliente, Porto origem, Porto destino, TipoCarga tipoCarga, Situacao situacao) {
        this.identificador = identificador;
        this.peso = peso;
        this.valorDeclarado = valorDeclarado;
        this.tempoMaximo = tempoMaximo;
        this.cliente = cliente;
        this.origem = origem;
        this.destino = destino;
        this.tipoCarga = tipoCarga;
        this.situacao = situacao;
    }

    public int getIdentificador() {
        return identificador;
    }

    public int getPeso() {
        return peso;
    }

    public double getValorDeclarado() {
        return valorDeclarado;
    }

    public int getTempoMaximo() {
        return tempoMaximo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Porto getOrigem() {
        return origem;
    }

    public Porto getDestino() {
        return destino;
    }

    public TipoCarga getTipoCarga() {
        return tipoCarga;
    }

    public Situacao getSituacao() {
        return situacao;
    }
}
