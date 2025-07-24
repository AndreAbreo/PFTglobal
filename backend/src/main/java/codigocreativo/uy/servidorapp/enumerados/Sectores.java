package codigocreativo.uy.servidorapp.enumerados;

public enum Sectores{
    POLICLINICO("Policlínico"),
    INTERNACION("Internación"),
    EMERGENCIA("Emergencia"),
    CTI("CTI"),
    OTRO("Otro");

    private String valor;

    Sectores(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
