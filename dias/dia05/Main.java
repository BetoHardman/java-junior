public class Main {
    public static void main(String[] args) {
        Cliente a = new Cliente("Luna Toys", "61993687124");
        Cliente b = new Cliente("Bruna Severo", "61999990000");

        System.out.println(a.resumo());
        System.out.println(b.resumo());

        b.mudarStatus("fechado");

        System.out.println("--- depois ---");
        System.out.println(a.resumo());
        System.out.println(b.resumo());

        Cliente[] carteira = {a, b};
        int fechados = 0;
        for (Cliente c : carteira) {
            if (c.status.equals("fechado")) {
                fechados = fechados + 1;
            }
        }
        System.out.println("Fechados: " + fechados);
    }
}
