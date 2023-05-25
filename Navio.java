public class Navio {
    private String nome;
    private double velocidade;
    private double autonomia;
    private double custoPorMilhaBasico;

    public Navio(String nome, double velocidade, double autonomia, double custoPorMilhaBasico) {
        this.nome = nome;
        this.velocidade = velocidade;
        this.autonomia = autonomia;
        this.custoPorMilhaBasico = custoPorMilhaBasico;
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
}
