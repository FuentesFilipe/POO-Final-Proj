package app;

import colecoes.*;
import enums.*;
import enums.Prioridade;
import modelo.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {
    private Scanner entradaUsuario = null;
    private int escolha = -1;

    // Construtor
    public App() {
        entradaUsuario = new Scanner(System.in);
    }

    public void executa() {
        Inventario inventario = new Inventario();
        Clientela clientela = new Clientela();
        Frota frota = new Frota();
        TipoInventario tipoInventario = new TipoInventario();
        Portuario portuario = new Portuario();
        Rotas rotas = new Rotas();

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
                    mostraCargasCadastradas(inventario, clientela, tipoInventario, portuario);
                    break;
                case 8:
                    System.out.println("Carregando dados iniciais...");
                    clientela.carregaDadosIniciais();
                    portuario.carregaDadosIniciais();
                    frota.carregaDadosIniciais();
                    tipoInventario.carregaDadosIniciais();
                    inventario.carregaDadosIniciais();
                    rotas.carregaDadosIniciais();
                    break;
                case 9:
                    fretarCargasPendentes(inventario, frota, tipoInventario, portuario, rotas);
                    break;
                case 10:
                    System.out.println("Digite o nome que deseja para o save:");
                    String nomeSave = entradaUsuario.nextLine();
                    System.out.println("Salvando dados...");
                    salvaDados(inventario, portuario, clientela, tipoInventario, rotas, frota, nomeSave);
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

    private static void fretarCargasPendentes(Inventario inventario, Frota frota, TipoInventario tipoInventario, Portuario portuario, Rotas rotas) {
        System.out.println("Verificando cargas pendentes...");
        if (inventario.getCargasPendentes().isEmpty()) {
            System.out.println("Não há cargas pendentes.");
            return;
        }
        if (frota.getNavios().isEmpty()) {
            System.out.println("Frota vazia (não há navios).");
            return;
        }

        boolean navioDisponivel = false;
        for (Carga carga : inventario.getCargasPendentes()) {
            // pegar a distancia da rota
            double totalDistancia;
            Distancia rota = rotas.procuraRota(carga.getCodPortoOrigem(), carga.getCodPortoDestino());
            if (rota != null)
                totalDistancia = rota.getValor();
            else
                totalDistancia = 100;

            // verificar se algum navio disponivel pode fretar a carga
            for (Navio navio : frota.getNavios().values()) {
                if (navio.getAutonomia() >= totalDistancia && !navio.temCarga()) {
                    navio.designaCarga(carga);
                    carga.atualizarSituacao(Situacao.LOCADO);

                    // calcular o valor do frete por final
                    // formaula final eh: precoPorDistancia + precoPorPeso + custoPorRegiao
                    // precoPorDistancia depende se prioridade da carga eh barata ou rapida, e da distancia
                    // se BARATO entao precoPorDistancia = totalDistancia * navio.custoPorMilhaBasico
                    // se RAPIDO entao precoPorDistancia = totalDistancia * (navio.custoPorMilhaBasico * 2)
                    double precoPorDistancia;
                    if (carga.getPrioridade() == Prioridade.BARATO)
                        precoPorDistancia = totalDistancia * navio.getCustoPorMilhaBasico();
                    else
                        precoPorDistancia = totalDistancia * (navio.getCustoPorMilhaBasico()*2);

                    // precoPorPeso depende do tipo de carga
                    // se PERECIVEL entao precoPorPeso = carga.peso * 2;
                    // se DURAVEL entao precoPorPeso = (carga.peso * 1.5) + (tipoCarga.percentIpi * carga.valorDeclarado)
                    double precoPorPeso;
                    var aux = tipoInventario.procuraTipoCarga(carga.getCodTipoCarga());
                    if (aux instanceof Perecivel)
                        precoPorPeso = carga.getPeso() * 2;
                    else
                        precoPorPeso = (carga.getPeso() * 1.5) + (((Duravel) aux).getPercentIpi() * carga.getValorDeclarado());


                    // custoPorRegiao eh valor fixo dependendo do transporte
                    // se apenas no brasil entao custoPorRegiao = 10_000
                    // se internacional entao custoPorRegiao = 50_000
                    double custoPorRegiao;
                    Porto auxOrigem = portuario.procuraPorto(carga.getCodPortoOrigem());
                    Porto auxDestino = portuario.procuraPorto(carga.getCodPortoDestino());
                    if (auxOrigem.getPais().equalsIgnoreCase("brasil") && auxDestino.getPais().equalsIgnoreCase("brasil"))
                        custoPorRegiao = 10_000;
                    else
                        custoPorRegiao = 50_000;

                    // calcular o valor do frete
                    double valorFrete = precoPorDistancia + precoPorPeso + custoPorRegiao;
                    System.out.printf("Carga %d designada para o navio %s. Valor do frete: %.2f\n", carga.getIdentificador(), navio.getNome(), valorFrete);
                    navioDisponivel = true;
                    break;

                }
            }

            if (navioDisponivel)
                continue; // codigo auxiliar para pular para a proxima carga

            // se chegou aqui, nao ha nenhum navio disponivel para fretar a carga
            // atualizar situacao da carga para CANCELADO e removela da lista de cargas pendentes
            carga.atualizarSituacao(Situacao.CANCELADO);
            inventario.getCargasPendentes().remove(carga);
            System.out.printf("Carga %d cancelada por falta de navios disponíveis.\n", carga.getIdentificador());
        }
    }


    /**
     * Mostra todas as cargas cadastradas: todos os dados das
     * cargas, incluindo os dados dos portos de origem e destino, do cliente, do tipo de carga
     * e da situação; se a carga possui um navio designado, mostra os dados do navio e o
     * valor final do frete [se não há cargas, mostra uma mensagem de erro]
     * @param inventario Colecao de cargas do sistema
     * @param clientela Colecao de clientes do sistema
     * @param tipoInventario Colecao de tipos de cargas do sistema
     * @param portuario Colecao de portos do sistema
     */
    private static void mostraCargasCadastradas(Inventario inventario, Clientela clientela, TipoInventario tipoInventario, Portuario portuario) {
        if (!inventario.getCargas().isEmpty()) {
            System.out.println("Cargas atuais do sistema:");
            // para cada carga do sistema, mostrar infos da carga, dos portos de origem e destino,
            // do cliente e do tipo de carga
            for (Carga carga : inventario.getCargas().values()) {
                System.out.printf("Carga %d:%n", carga.getIdentificador());
                System.out.println(carga);
                System.out.println("Porto de origem:");
                System.out.println(portuario.getPortos().get(carga.getCodPortoOrigem()));
                System.out.println("Porto de destino:");
                System.out.println(portuario.getPortos().get(carga.getCodPortoDestino()));
                System.out.println("Cliente:");
                System.out.println(clientela.getClientes().get(carga.getCodCliente()));
                System.out.println("Tipo de carga:");
                System.out.println(tipoInventario.getTipoCargas().get(carga.getCodTipoCarga()));
                System.out.println("--------------------------------------------------");
            }
        } else {
            System.err.println("Não há cargas cadastradas no sistema!");
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

    private void salvaDados(Inventario inventario, Portuario portuario,
                            Clientela clientela, TipoInventario tipoInventario,
                            Rotas rotas, Frota frota, String nomeArquivo) {
        System.out.println("Salvando dados...");
        try {
            String caminhoDiretorio = "dadosSalvos";
            String caminhoArq = caminhoDiretorio + File.separator + nomeArquivo;
            FileOutputStream fileOut = new FileOutputStream(caminhoArq);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(inventario);
            out.writeObject(portuario);
            out.writeObject(clientela);
            out.writeObject(tipoInventario);
            out.writeObject(rotas);
            out.writeObject(frota);
            out.close();
            fileOut.close();
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro: Falha ao salvar dados.");
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
