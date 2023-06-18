package modelo;

import enums.Prioridade;
import enums.Situacao;


public class Carga {
    private int identificador;
    private int peso;
    private double valorDeclarado;
    private int tempoMaximo;
    private int codCliente;
    private int codPortoOrigem;
    private int codPortoDestino;
    private int codTipoCarga;
    private Situacao situacao;
    private Prioridade prioridade;

    public Carga(
            int identificador, int codCliente, int codPortoOrigem, int codPortoDestino, int peso,
            double valorDeclarado, int tempoMaximo, int codTipoCarga, Prioridade prioridade) {
        this.identificador = identificador;
        this.codCliente = codCliente;
        this.peso = peso;
        this.valorDeclarado = valorDeclarado;
        this.tempoMaximo = tempoMaximo;
        this.codPortoOrigem = codPortoOrigem;
        this.codPortoDestino = codPortoDestino;
        this.codTipoCarga = codTipoCarga;
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

    public int getCodCliente() {
        return codCliente;
    }

    public int getCodPortoOrigem() {
        return codPortoOrigem;
    }

    public int getCodPortoDestino() {
        return codPortoDestino;
    }

    public int getCodTipoCarga() {
        return codTipoCarga;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    @Override
    public String toString() {
        return String.format(
                "%d;%d;%d;%d;%d;%.2f;%d;%d;%s;%s",
                identificador, codCliente, codPortoOrigem, codPortoDestino, peso, valorDeclarado,
                tempoMaximo, codTipoCarga, situacao, prioridade);
    }
}
