package modelo;

public class Navio {
    private String nome;
    private double velocidade;
    private double autonomia;
    private double custoPorMilhaBasico;
    private Carga carga;

    public Navio(String nome, double velocidade, double autonomia, double custoPorMilhaBasico) {
        this.nome = nome;
        this.velocidade = velocidade;
        this.autonomia = autonomia;
        this.custoPorMilhaBasico = custoPorMilhaBasico;
        this.carga = null;
    }

    public String getNome() {
        return nome;
    }

    public double getVelocidade() {
        return velocidade;
    }

    public double getAutonomia() {
        return autonomia;
    }

    public double getCustoPorMilhaBasico() {
        return custoPorMilhaBasico;
    }

    public Carga getCarga() {
        return carga;
    }

    public void designaCarga(Carga carga) {
        this.carga = carga;
    }

    public boolean temCarga() {
        return carga != null;
    }

    @Override
    public String toString() {
        return String.format("%s;%.2f;%.2f;%.2f", nome, velocidade, autonomia, custoPorMilhaBasico);
    }
}
