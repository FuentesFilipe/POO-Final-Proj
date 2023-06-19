package modelo;

import java.io.Serializable;

public class Duravel extends TipoCarga implements Serializable {
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

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;%.2f%%", super.toString(), "DURAVEL", setor, materialPrincipal, percentIpi);
    }
}
