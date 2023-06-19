package modelo;

import java.io.Serializable;

public class Porto implements Serializable {
    private int id;
    private String nome;
    private String pais;

    public Porto(int id, String nome, String pais) {
        this.id = id;
        this.nome = nome;
        this.pais = pais;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getPais() {
        return pais;
    }

    @Override
    public String toString() {
        return String.format("%d;%s;%s", id, nome, pais);
    }
}
