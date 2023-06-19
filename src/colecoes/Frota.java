package colecoes;

import modelo.Navio;

import java.io.FileReader;
import java.io.Serializable;
import java.util.Scanner;
import java.util.TreeMap;

public class Frota implements Serializable {
    private TreeMap<String, Navio> navios;

    public Frota() {
        navios = new TreeMap<>();
    }

    public TreeMap<String, Navio> getNavios() {
        return navios;
    }

    public boolean existeNavio(String nome) {
        return navios.containsKey(nome);
    }

    public boolean addNavio(Navio navio) {
        return navios.putIfAbsent(navio.getNome(), navio) == null;
    }

    public Navio getNavio(String nome) {
        return navios.get(nome);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Navio navio : navios.values()) {
            sb.append(navio).append("\n");
        }
        return sb.toString();
    }

    /**
     * Le os dados iniciais do arquivo ...-NAVIOS.CSV
     * e os carrega na colecao de navios
     */
    public void carregaDadosIniciais() {
        try {
            Scanner entrada = new Scanner(new FileReader("src/dados/EXEMPLO-NAVIOS.CSV"));
            entrada.nextLine(); // Consome a primeira linha do arquivo (cabeçalho)
            while (entrada.hasNextLine()) {
            String linha = entrada.nextLine().replaceAll(",", ".");
                String[] dados = linha.split(";");
                String nome = dados[0];
                double velocidade = Double.parseDouble(dados[1]);
                double autonomia = Double.parseDouble(dados[2]);
                double custoPorMilhaBasico = Double.parseDouble(dados[3]);
                Navio navio = new Navio(nome, velocidade, autonomia, custoPorMilhaBasico);
                addNavio(navio);
            }
            entrada.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
