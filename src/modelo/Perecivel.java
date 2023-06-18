package modelo;

public class Perecivel extends TipoCarga {
    private String origem;
    private int tempoMaxVelocidade;

    public Perecivel(int numero, String descricao, String origem, int tempoMaxVelocidade) {
        super(numero, descricao);
        this.origem = origem;
        this.tempoMaxVelocidade = tempoMaxVelocidade;
    }

    public String getOrigem() {
        return origem;
    }

    public int getTempoMaxVelocidade() {
        return tempoMaxVelocidade;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%d", super.toString(), "PERECIVEL", origem, tempoMaxVelocidade);
    }
}
