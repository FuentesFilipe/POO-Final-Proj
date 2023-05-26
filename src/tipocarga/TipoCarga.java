package tipocarga;

public abstract class TipoCarga {
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
}
