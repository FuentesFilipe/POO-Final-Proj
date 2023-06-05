package colecoes;

import modelo.Navio;

import java.io.FileReader;
import java.util.Scanner;
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
