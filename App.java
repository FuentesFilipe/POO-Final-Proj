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

    }
}
