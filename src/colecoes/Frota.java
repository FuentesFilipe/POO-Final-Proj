package colecoes;

import modelo.Navio;

import java.util.TreeMap;

public class Frota {
    private TreeMap<String, Navio> navios;

    public Frota() {
        navios = new TreeMap<>();
    }

    public TreeMap<String, Navio> getNavios() {
        return navios;
    }

    public boolean addNavio(Navio navio) {
        return navios.putIfAbsent(navio.getNome(), navio) == null;
    }
}
