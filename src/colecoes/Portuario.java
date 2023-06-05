package colecoes;

import modelo.Navio;
import modelo.Porto;

import java.io.FileReader;
import java.util.Scanner;
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

    /**
     * Le os dados iniciais do arquivo ...-PORTOS.CSV
     * e os carrega na colecao de portos
     */
    public void carregaDadosIniciais() {
        try {
            Scanner entrada = new Scanner(new FileReader("src/dados/EXEMPLO-PORTOS.CSV"));
            entrada.nextLine(); // Consome a primeira linha do arquivo (cabeçalho)
            while (entrada.hasNextLine()) {
                String linha = entrada.nextLine();
                String[] dados = linha.split(";");
                int id = Integer.parseInt(dados[0]);
                String nome = dados[1];
                String pais = dados[2];
                Porto porto = new Porto(id, nome, pais);
                addPorto(porto);
            }
            entrada.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
