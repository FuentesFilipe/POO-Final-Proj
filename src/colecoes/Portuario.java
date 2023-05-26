package colecoes;

import modelo.Navio;
import modelo.Porto;

import java.util.TreeMap;

public class Portuario {
    private TreeMap<Integer, Porto> portos;

    public Portuario() {
        portos = new TreeMap<>();
    }

    public TreeMap<Integer, Porto> getPortos() {
        return portos;
    }

    public boolean addPorto(Porto porto) {
        return portos.putIfAbsent(porto.getId(), porto) == null;
    }
}
