package modelo;

import enums.Prioridade;
import enums.Situacao;


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
    private Prioridade prioridade;

    public Carga(
            int identificador, Cliente cliente, Porto origem, Porto destino, int peso,
            double valorDeclarado, int tempoMaximo, TipoCarga tipoCarga, Prioridade prioridade) {
        this.identificador = identificador;
        this.cliente = cliente;
        this.peso = peso;
        this.valorDeclarado = valorDeclarado;
        this.tempoMaximo = tempoMaximo;
        this.origem = origem;
        this.destino = destino;
        this.tipoCarga = tipoCarga;
        this.situacao = Situacao.PENDENTE;
        this.prioridade = prioridade;
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
