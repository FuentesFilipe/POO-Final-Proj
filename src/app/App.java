package app;

import colecoes.*;
//import dados.*;
//import enums.*;
//import modelo.*;


import java.io.*;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Scanner;

public class App {
    private Scanner entrada = null; // Atributo para entrada de dados
    private Scanner entradaUsuario = null;
    private int escolha = -1;

    // Construtor
    public App() {
        entradaUsuario = new Scanner(System.in);
        try {
            BufferedReader streamEntrada = new BufferedReader(new FileReader("dados.csv"));
            entrada = new Scanner(streamEntrada); // Usa como entrada um arquivo
            PrintStream streamSaida = new PrintStream (new File("resultado.csv"), Charset.forName("UTF-8"));
            System.setOut(streamSaida); // Usa como saida um arquivo
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void executa() {
        Inventario inventario = new Inventario();
        Clientela clientela = new Clientela();
        Frota frota = new Frota();
        TipoInventario tipoInventario = new TipoInventario();
        Portuario portuario = new Portuario();

        while(escolha != 0) {
            mostraMenu();
            escolha = entradaUsuario.nextInt();
            entradaUsuario.nextLine();
            System.out.println();

            switch(escolha) {
                case 8:
                    System.out.println("Carregando dados iniciais...");
                    clientela.carregaDadosIniciais();
                    portuario.carregaDadosIniciais();
                    frota.carregaDadosIniciais();
                    break;
                case 0:
                    System.out.println("Finalizando sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    /**
     * Mostra o menu de opções do sistema
     */
    private void mostraMenu() {
        System.out.println("Menu:");
        System.out.println("1. Cadastrar novo porto");
        System.out.println("2. Cadastrar novo navio");
        System.out.println("3. Cadastrar novo cliente");
        System.out.println("4. Cadastrar novo tipo de carga");
        System.out.println("5. Cadastrar nova carga");
        System.out.println("6. Consultar todas as cargas");
        System.out.println("7. Alterar a situação de uma carga");
        System.out.println("8. Carregar dados iniciais");
        System.out.println("9. Fretar cargas");
        System.out.println("10. Salvar dados");
        System.out.println("11. Carregar dados");
        System.out.println("0. Finalizar sistema");
        System.out.print("Escolha uma opção: ");
    }
}
