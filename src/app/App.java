package app;

import colecoes.*;
//import dados.*;
//import enums.*;
import enums.Prioridade;
import modelo.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
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
            PrintStream streamSaida = new PrintStream(new File("resultado.csv"), Charset.forName("UTF-8"));
            System.setOut(streamSaida); // Usa como saida um arquivo
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void executa() {
        Inventario inventario = new Inventario();
        Clientela clientela = new Clientela();
        Frota frota = new Frota();
        TipoInventario tipoInventario = new TipoInventario();
        Portuario portuario = new Portuario();

        while (escolha != 0) {
            mostraMenu();
            escolha = entradaUsuario.nextInt();
            entradaUsuario.nextLine();
            System.out.println();

            switch (escolha) {
                case 1:
                    criarNovoPorto(portuario);
                    break;
                case 2:
                    criarNovoNavio(frota);
                    break;
                case 3:
                    criarNovoCliente(clientela);
                    break;
                case 4:
                    criarNovoTipoCarga(tipoInventario);
                    break;
                case 5:
                    criarNovaCarga(inventario);
                    checarCargas(inventario, portuario, clientela, tipoInventario);
                    break;
                case 6:
                    System.out.println("Cargas atuais do sistema:");

                    break;
                case 8:
                    System.out.println("Carregando dados iniciais...");
                    clientela.carregaDadosIniciais();
                    portuario.carregaDadosIniciais();
                    frota.carregaDadosIniciais();
                    tipoInventario.carregaDadosIniciais();
                    inventario.carregaDadosIniciais();
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
     * Cria uma nova carga para o sistema a partir da entrada do usuario,
     * tanto sucesso quanto falha eh informado ao usuario atraves de uma mensagem
     *
     * @param inventario Colecao de cargas do sistema
     */
    private void criarNovaCarga(Inventario inventario) {
        try {
            System.out.println("Cadastrando nova carga...");
            System.out.println("Informe o codigo da carga:");
            int codCarga = entradaUsuario.nextInt();
            entradaUsuario.nextLine();
            System.out.println("Informe o codigo do cliente:");
            int codCliente = entradaUsuario.nextInt();
            entradaUsuario.nextLine();
            System.out.println("Informe o codigo do porto de origem:");
            int codPortoOrigem = entradaUsuario.nextInt();
            entradaUsuario.nextLine();
            System.out.println("Informe o codigo do porto de destino:");
            int codPortoDestino = entradaUsuario.nextInt();
            entradaUsuario.nextLine();
            System.out.println("Informe o peso:");
            int peso = entradaUsuario.nextInt();
            entradaUsuario.nextLine();
            System.out.println("Declare o valor:");
            double valorDeclarado = entradaUsuario.nextDouble();
            System.out.println("Informe o tempo maximo:");
            int tempoMaximo = entradaUsuario.nextInt();
            entradaUsuario.nextLine();
            System.out.println("Informe o codigo do tipo de carga:");
            int codTipoCarga = entradaUsuario.nextInt();
            entradaUsuario.nextLine();
            System.out.println("Informe a prioridade:");
            Prioridade prioridade = Prioridade.valueOf(entradaUsuario.nextLine().toUpperCase());
            Carga carga = new Carga(codCarga, codCliente, codPortoOrigem, codPortoDestino, peso,
                    valorDeclarado, tempoMaximo, codTipoCarga, prioridade);

            if (inventario.addCarga(carga)) {
                System.out.println("Carga cadastrada com sucesso!");
            } else {
                System.err.println("Carga não cadastrada!");
            }
        } catch (InputMismatchException e) {
            System.err.println("Erro: Entrada inválida para algum codigo.");
        } catch (NoSuchElementException e) {
            System.err.println("Erro: Nenhuma entrada fornecida.");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: Prioridade inválida.");
        }
    }

    /**
     * Cria um novo tipo de carga para o sistema a partir da entrada do usuario,
     * tanto sucesso quanto falha eh informado ao usuario atraves de uma mensagem
     *
     * @param tipoInventario Colecao de portos do sistema
     */
    private void criarNovoTipoCarga(TipoInventario tipoInventario) {
        try {
            System.out.println("Cadastrando novo tipo de carga...");
            System.out.println("Informe o codigo do tipo de carga:");
            int codTipoCarga = entradaUsuario.nextInt();
            entradaUsuario.nextLine();
            System.out.println("Informe a descricao do tipo de carga:");
            String descTipoCarga = entradaUsuario.nextLine();
            System.out.println("Informe se duravel ou perecivel:");
            String catTipoCarga = entradaUsuario.nextLine();
            TipoCarga tipoCarga = null;
            switch (catTipoCarga.toLowerCase()) {
                case "duravel":
                    System.out.println("Informe o setor:");
                    String setor = entradaUsuario.nextLine();
                    System.out.println("Informe o material principal:");
                    String materialPrincipal = entradaUsuario.nextLine();
                    System.out.println("Informe o percentual de IPI:");
                    double percentIpi = entradaUsuario.nextDouble();
                    tipoCarga = new Duravel(codTipoCarga, descTipoCarga, setor, materialPrincipal, percentIpi);
                    break;
                case "perecivel":
                    System.out.println("Informe a origem:");
                    String origem = entradaUsuario.nextLine();
                    System.out.println("Informe o tempo maximo de velocidade:");
                    int tempoMaxVelocidade = entradaUsuario.nextInt();
                    tipoCarga = new Perecivel(codTipoCarga, descTipoCarga, origem, tempoMaxVelocidade);
                    break;
                default:
                    System.err.println("Categoria inválida!");
                    break;
            }

            if (tipoCarga != null) {
                if (tipoInventario.addTipoCarga(tipoCarga)) {
                    System.out.println("Tipo de carga cadastrado com sucesso!");
                } else {
                    System.err.println("Tipo de carga já cadastrado!");
                }
            }
        } catch (InputMismatchException e) {
            System.err.println("Erro: Entrada inválida para o codigo do tipo de carga.");
        } catch (NoSuchElementException e) {
            System.err.println("Erro: Nenhuma entrada fornecida.");
        }
    }

    /**
     * Cria um novo cliente para o sistema a partir da entrada do usuario,
     * tanto sucesso quanto falha eh informado ao usuario atraves de uma mensagem
     *
     * @param clientela Colecao de clientes do sistema
     */
    private void criarNovoCliente(Clientela clientela) {
        try {
            System.out.println("Cadastrando novo cliente...");
            System.out.println("Informe o codigo do cliente:");
            int codCliente = entradaUsuario.nextInt();
            entradaUsuario.nextLine();
            System.out.println("Informe o nome do cliente:");
            String nomeCliente = entradaUsuario.nextLine();
            System.out.println("Informe o email do cliente:");
            String emailCliente = entradaUsuario.nextLine();
            Cliente cliente = new Cliente(codCliente, nomeCliente, emailCliente);
            if (clientela.addCliente(cliente)) {
                System.out.println("Cliente cadastrado com sucesso!");
            } else {
                System.out.println("Cliente já cadastrado!");
            }
        } catch (InputMismatchException e) {
            System.err.println("Erro: Entrada inválida para o codigo do cliente.");
        } catch (NoSuchElementException e) {
            System.err.println("Erro: Nenhuma entrada fornecida.");
        }
    }

    /**
     * Cria um novo navio para o sistema a partir da entrada do usuario,
     * tanto sucesso quanto falha eh informado ao usuario atraves de uma mensagem
     *
     * @param frota Colecao de navios do sistema
     */
    private void criarNovoNavio(Frota frota) {
        try {
            System.out.println("Cadastrando novo navio...");
            System.out.println("Informe o nome do navio:");
            String nomeNavio = entradaUsuario.nextLine();
            System.out.println("Informe a velocidade do navio:");
            double velocidadeNavio = entradaUsuario.nextDouble();
            System.out.println("Informe a autonomia do navio:");
            double autonomiaNavio = entradaUsuario.nextDouble();
            System.out.println("Informe o custo por milha do navio:");
            double custoPorMilhaNavio = entradaUsuario.nextDouble();
            Navio navio = new Navio(nomeNavio, velocidadeNavio, autonomiaNavio, custoPorMilhaNavio);
            if (frota.addNavio(navio)) {
                System.out.println("Navio cadastrado com sucesso!");
            } else {
                System.out.println("Erro: Navio já cadastrado!");
            }
        } catch (InputMismatchException e) {
            System.err.println("Erro: Entrada inválida para o algum dado do navio.");
        } catch (NoSuchElementException e) {
            System.err.println("Erro: Nenhuma entrada fornecida.");
        }
    }

    /**
     * Cria um novo porto para o sistema a partir da entrada do usuario,
     * tanto sucesso quanto falha eh informado ao usuario atraves de uma mensagem
     *
     * @param portuario Colecao de portos do sistema
     */
    private void criarNovoPorto(Portuario portuario) {
        try {
            System.out.println("Cadastrando novo porto...");
            System.out.println("Informe o id do porto:");
            int idPorto = entradaUsuario.nextInt();
            entradaUsuario.nextLine();
            System.out.println("Informe o nome do porto:");
            String nomePorto = entradaUsuario.nextLine();
            System.out.println("Informe o país do porto:");
            String paisPorto = entradaUsuario.nextLine();
            Porto porto = new Porto(idPorto, nomePorto, paisPorto);
            if (portuario.addPorto(porto)) {
                System.out.println("Porto cadastrado com sucesso!");
            } else {
                System.err.println("Erro: Porto já cadastrado!");
            }
        } catch (InputMismatchException e) {
            System.err.println("Erro: Entrada inválida para o id do porto.");
        } catch (NoSuchElementException e) {
            System.err.println("Erro: Nenhuma entrada fornecida.");
        }
    }

    // Para poder checar se as cargas cadastradas sao validas eh necessario checar
    // se os portos, clientes e tipos de carga ja foram cadastrados. O metodo abaixo
    // vai checar se todos os dados necessarios ja foram cadastrados, e deletar qualquer
    // carga que nao atenda a esse criterio.
    private void checarCargas(Inventario inventario, Portuario portuario,
                              Clientela clientela, TipoInventario tipoInventario) {
        System.out.println("Checando cargas...");
        for (Carga carga : inventario.getCargas().values()) {
            int codCliente = carga.getCodCliente();
            int codPortoOrigem = carga.getCodPortoOrigem();
            int codPortoDestino = carga.getCodPortoDestino();
            int codTipoCarga = carga.getCodTipoCarga();

            // se a chave nao exite nos clientes, remove a carga
            if (!clientela.existeCod(codCliente)) {
                inventario.removeCarga(carga);
                System.out.printf("Cliente %d nao existe, removendo carga %d%n", codCliente, carga.getIdentificador());
                continue;
            }

            // se a chave nao existe nos possiveis portos, remove a carga
            if (!portuario.existePorto(codPortoOrigem) || !portuario.existePorto(codPortoDestino)) {
                inventario.removeCarga(carga);
                System.out.printf("Porto %d ou %d nao existe, removendo carga %d%n", codPortoOrigem, codPortoDestino, carga.getIdentificador());
                continue;
            }

            if (!tipoInventario.existeTipoCarga(codTipoCarga)) {
                inventario.removeCarga(carga);
                System.out.printf("Tipo de carga %d nao existe, removendo carga %d%n", codTipoCarga, carga.getIdentificador());
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
