package modelo;

import java.util.ArrayList;

public class Cliente {
    private int cod;
    private String nome;
    private String email;
    private ArrayList<Carga> cargas;

    public Cliente(int cod, String nome, String email) {
        this.cod = cod;
        this.nome = nome;
        this.email = email;
        cargas = new ArrayList<>(10);
    }

    public int getCod() {
        return cod;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Carga> getCargas() {
        return cargas;
    }
}
