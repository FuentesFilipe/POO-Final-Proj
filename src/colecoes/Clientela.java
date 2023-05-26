package colecoes;

import modelo.Cliente;
import modelo.Navio;

import java.util.TreeMap;
public class Clientela {
    private TreeMap<Integer, Cliente> clientes;

    public Clientela() {
        clientes = new TreeMap<>();
    }

    public TreeMap<Integer, Cliente> getClientes() {
        return clientes;
    }

    public boolean existeEmail(String email) {
        for (Cliente cliente : clientes.values()) {
            if (cliente.getEmail().equals(email))
                return true;
        }
        return false;
    }

    public boolean addCliente(Cliente cliente) {
        int cod = cliente.getCod();
        String email = cliente.getEmail();

        if (clientes.containsKey(cod) || existeEmail(email)) {
            return false;
        }

        clientes.put(cod, cliente);
        return true;
    }
}
