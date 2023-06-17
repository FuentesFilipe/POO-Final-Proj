package colecoes;

import modelo.Cliente;
import modelo.Navio;

import java.io.FileReader;
import java.util.Scanner;
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

    public boolean existeCod(int cod) {
        return clientes.containsKey(cod);
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

    /**
     * Le os dados iniciais do arquivo ...-CLIENTES.CSV
     * e os carrega na colecao de clientes
     */
    public void carregaDadosIniciais() {
        try {
            Scanner entrada = new Scanner(new FileReader("src/dados/EXEMPLO-CLIENTES.CSV"));
            entrada.nextLine(); // Consome a primeira linha do arquivo (cabeçalho)
            while (entrada.hasNextLine()) {
                String linha = entrada.nextLine();
                String[] dados = linha.split(";");
                int cod = Integer.parseInt(dados[0]);
                String nome = dados[1];
                String email = dados[2];
                Cliente cliente = new Cliente(cod, nome, email);
                addCliente(cliente);
            }
            entrada.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
