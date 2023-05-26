package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Scanner;

public class App {
    private Scanner entrada = null; // Atributo para entrada de dados
    // Construtor
    public App() {
        Locale.setDefault(Locale.ENGLISH);
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
        mostraMenu();
    }

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
        System.out.println("12. Finalizar sistema");
        System.out.print("Escolha uma opção: ");
    }
}
