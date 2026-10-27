public class Cliente {
    protected String nome;
    protected String whatsapp;
    protected String status;

    public Cliente(String nome, String whatsapp) {
        this.nome = nome;
        this.whatsapp = whatsapp;
        this.status = "novo";
    }

    public double valorHora() {
        return 120.0;
    }

    public String resumo() {
        return nome + " (" + whatsapp + ") - " + status;
    }
}
