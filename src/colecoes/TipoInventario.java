package colecoes;

import modelo.Navio;
import modelo.Porto;
import modelo.tipocarga.*;

import java.io.FileReader;
import java.util.Scanner;
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

    /**
     * Le os dados iniciais do arquivo ...-TIPOSCARGAS.CSV
     * e os carrega na colecao de tipos de carga
     */
    public void carregaDadosIniciais() {
        try {
            Scanner entrada = new Scanner(new FileReader("src/dados/EXEMPLO-NAVIOS.CSV"));
            entrada.nextLine(); // Consome a primeira linha do arquivo (cabeçalho)
            while(entrada.hasNextLine()) {
                String linha = entrada.nextLine().replaceAll(",", ".");
                String[] dados = linha.split(";");
                int numero = Integer.parseInt(dados[0]);
                String descricao = dados[1];
                if (dados[2].equals("DURAVEL")) {
                    String material = dados[3];
                    String embalagem = dados[4];
                    double valor = Double.parseDouble(dados[5]);
                    TipoCarga tipoCarga = new Duravel(numero, descricao, material, embalagem, valor);
                    addTipoCarga(tipoCarga);
                } else {
                    String origem = dados[3];
                    int tempoMaxVelocidade = Integer.parseInt(dados[4]);
                    TipoCarga tipoCarga = new Perecivel(numero, descricao, origem, tempoMaxVelocidade);
                    addTipoCarga(tipoCarga);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
