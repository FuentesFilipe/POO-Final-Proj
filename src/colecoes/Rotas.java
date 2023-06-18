package colecoes;

import modelo.Distancia;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Rotas {
    private ArrayList<Distancia> rotas;

    public Rotas() {
        rotas = new ArrayList<>();
    }

    public ArrayList<Distancia> getRotas() {
        return rotas;
    }

    public boolean existeRota(int origem, int destino) {
        for (Distancia rota : rotas) {
            if (rota.getOrigem() == origem && rota.getDestino() == destino) {
                return true;
            }
        }
        return false;
    }

    public boolean addRota(Distancia rota) {
        if (existeRota(rota.getOrigem(), rota.getDestino())) {
            return false;
        }
        return rotas.add(rota);
    }

    public boolean removeRota(int origem, int destino) {
        for (Distancia rota : rotas) {
            if (rota.getOrigem() == origem && rota.getDestino() == destino) {
                return rotas.remove(rota);
            }
        }
        return false;
    }

    public void carregaDadosIniciais() {
        try {
            Scanner entrada = new Scanner(new FileReader("src/dados/EXEMPLO-DISTANCIAS.CSV"));
            entrada.nextLine(); // Consome a primeira linha do arquivo (cabeçalho)
            while (entrada.hasNextLine()) {
                String linha = entrada.nextLine();
                String[] dados = linha.split(";");
                int origem = Integer.parseInt(dados[0]);
                int destino = Integer.parseInt(dados[1]);
                double valor = Double.parseDouble(dados[2]);
                Distancia rota = new Distancia(origem, destino, valor);
                addRota(rota);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
