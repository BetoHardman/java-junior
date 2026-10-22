public class Main {
    public static void main(String[] args) {
        Cliente a = new Cliente("Luna Toys", "61993687124");
        ClienteVip b = new ClienteVip("Bruna Severo", "61999990000", 20);

        System.out.println(a.resumo());
        System.out.println(b.resumo());
        System.out.println("Hora A: " + a.valorHora());
        System.out.println("Hora B: " + b.valorHora());

        System.out.println("--- polimorfismo ---");
        Cliente[] carteira = {a, b};
        for (Cliente c : carteira) {
            System.out.println(c.resumo() + " -> " + c.valorHora());
        }
    }
}
