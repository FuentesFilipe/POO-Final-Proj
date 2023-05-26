package colecoes;

import modelo.Porto;
import modelo.tipocarga.*;
import java.util.TreeMap;

public class TipoInventario {
    private TreeMap<Integer, TipoCarga> tipoCargas;

    public TipoInventario() {
        tipoCargas = new TreeMap<>();
    }

    public TreeMap<Integer, TipoCarga> getTipoCargas() {
        return tipoCargas;
    }

    public boolean addTipoCarga(TipoCarga tipoCarga) {
        return tipoCargas.putIfAbsent(tipoCarga.getNumero(), tipoCarga) == null;
    }
}
