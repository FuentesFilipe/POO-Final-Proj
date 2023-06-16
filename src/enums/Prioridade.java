package enums;

public enum Prioridade {
    RAPIDO("Rápido"), BARATO("Barato");

    private final String prioridade;

    Prioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getPrioridade() {
        return prioridade;
    }
}
