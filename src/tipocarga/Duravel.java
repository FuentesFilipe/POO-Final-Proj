package tipocarga;

public class Duravel extends TipoCarga {
    private String setor;
    private String materialPrincipal;
    private double percentIpi;

    public Duravel(int numero, String descricao, String setor, String materialPrincipal, double percentIpi) {
        super(numero, descricao);
        this.setor = setor;
        this.materialPrincipal = materialPrincipal;
        this.percentIpi = percentIpi;
    }

    public String getSetor() {
        return setor;
    }

    public String getMaterialPrincipal() {
        return materialPrincipal;
    }

    public double getPercentIpi() {
        return percentIpi;
    }
}
