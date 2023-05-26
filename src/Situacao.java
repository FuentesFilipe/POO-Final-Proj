public enum Situacao {
    PENDENTE("Pendente"), LOCADO("Locado"), CANCELADO("Cancelado"), FINALIZADO("Finalizado");

    private final String status;

    Situacao(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
