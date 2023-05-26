package colecoes;

import modelo.Carga;

import java.util.Queue;
import java.util.TreeMap;
import java.util.LinkedList;

public class Inventario {
    private TreeMap<Integer, Carga> cargas;
    private Queue<Carga> cargasPendentes;

    public Inventario() {
        cargas = new TreeMap<>();
        cargasPendentes = new LinkedList<>();
    }

    public TreeMap<Integer, Carga> getCargas() {
        return cargas;
    }

    public Queue<Carga> getCargasPendentes() {
        return cargasPendentes;
    }

    public boolean addCarga(Carga carga) {
        if (cargas.putIfAbsent(carga.getIdentificador(), carga) == null) {
            cargasPendentes.offer(carga);
            return true;
        }
        return false;
    }
}
