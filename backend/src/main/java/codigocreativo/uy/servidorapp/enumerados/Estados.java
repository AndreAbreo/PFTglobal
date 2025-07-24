package codigocreativo.uy.servidorapp.enumerados;

public enum Estados {
    ACTIVO("Activo"),
    INACTIVO("Inactivo"),
    SIN_VALIDAR("Sin validar");

    private String valor;

    Estados(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor;
    }
}
