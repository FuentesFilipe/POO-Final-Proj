package modelo;

import enums.Prioridade;
import enums.Situacao;

import java.io.Serializable;
import java.util.Objects;


public class Carga implements Serializable {
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

    public boolean atualizarSituacao(Situacao novaSituacao) {
        if (this.situacao == Situacao.FINALIZADO) {
            System.err.println("Carga se encontra no estado FINALIZADO, não pode ser alterada.");
            return false;
        }

        if (novaSituacao == Situacao.LOCADO) {
            if (this.situacao == Situacao.PENDENTE) {
                this.situacao = Situacao.LOCADO;
                return true;
            } else {
                System.err.println("A carga só pode ser alterada para LOCADO se estiver na situação PENDENTE.");
                return false;
            }
        } else if (novaSituacao == Situacao.FINALIZADO) {
            if (this.situacao == Situacao.LOCADO) {
                this.situacao = Situacao.FINALIZADO;
                return true;
            } else {
                System.err.println("A carga só pode ser alterada para FINALIZADO se estiver na situação LOCADO.");
                return false;
            }
        } else if (novaSituacao == Situacao.CANCELADO) {
            this.situacao = Situacao.CANCELADO;
            return true;
        }
        System.out.println("Situação inválida.");
        return false;
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
