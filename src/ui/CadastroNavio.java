package ui;
import modelo.Navio;
import javax.swing.*;
import java.awt.*;

/**
 * Classe que representa a tela de cadastro de navios.
 *
 * @see Navio
 */
public class CadastroNavio extends JFrame {
    private JTextField nome;
    private JTextField velocidade;
    private JTextField autonomia;
    private JTextField custoPorMilhaBasico;

    // construtor
    public CadastroNavio() {
        super();
        nome = new JTextField(20);
        velocidade = new JTextField(20);
        autonomia = new JTextField(20);
        custoPorMilhaBasico = new JTextField(20);

        FlowLayout layout = new FlowLayout();
        JPanel painel = new JPanel();
        painel.setLayout(layout);
        painel.add(nome);
        painel.add(velocidade);
        painel.add(autonomia);
        painel.add(custoPorMilhaBasico);
        this.setSize(800, 600);
        this.setTitle("Cadastro de Navios");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
