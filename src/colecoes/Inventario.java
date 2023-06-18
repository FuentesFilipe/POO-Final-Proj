package colecoes;

import enums.Prioridade;
import modelo.Carga;

import java.io.FileReader;
import java.util.Queue;
import java.util.Scanner;
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

    public boolean existeCarga(int identificador) {
        return cargas.containsKey(identificador);
    }

    public boolean addCarga(Carga carga) {
        if (cargas.putIfAbsent(carga.getIdentificador(), carga) == null) {
            cargasPendentes.offer(carga);
            return true;
        }
        return false;
    }

    public boolean removeCarga(Carga carga) {
        if (cargas.remove(carga.getIdentificador()) != null) {
            cargasPendentes.remove(carga);
            return true;
        }
        return false;
    }

    public void carregaDadosIniciais() {
        try {
            Scanner entrada = new Scanner(new FileReader("src/dados/EXEMPLO-CARGAS.CSV"));
            entrada.nextLine(); // Consome a primeira linha do arquivo (cabeçalho)
            while (entrada.hasNextLine()) {
                String linha = entrada.nextLine().replaceAll(",", ".");
                String[] dados = linha.split(";");
                int codigo = Integer.parseInt(dados[0]);
                int codCliente = Integer.parseInt(dados[1]);
                int codOrigem = Integer.parseInt(dados[2]);
                int codDestino = Integer.parseInt(dados[3]);
                int peso = Integer.parseInt(dados[4]);
                double valorDeclarado = Double.parseDouble(dados[5]);
                int tempoMaxEntrega = Integer.parseInt(dados[6]);
                int codTipoCarga = Integer.parseInt(dados[7]);
                Prioridade prioridade = Prioridade.valueOf(dados[8]);
                Carga carga = new Carga(
                        codigo, codCliente, codOrigem, codDestino, peso, valorDeclarado, tempoMaxEntrega,
                        codTipoCarga, prioridade);
                addCarga(carga);
            }
            entrada.close();
        } catch (Exception e) {
            System.err.println(e);
        }

    }
}
