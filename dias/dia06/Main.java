public class Main {
    public static void main(String[] args) {
        Cliente a = new Cliente("Luna Toys", "61993687124");
        System.out.println(a.resumo());

        System.out.println("--- tentando mudar ---");
        System.out.println("para fechado: " + a.setStatus("fechado"));
        System.out.println(a.resumo());

        System.out.println("para banana: " + a.setStatus("banana"));
        System.out.println(a.resumo());

        System.out.println("--- lendo pelos getters ---");
        System.out.println("Nome: " + a.getNome());
        System.out.println("Whats: " + a.getWhatsapp());
        System.out.println("Status: " + a.getStatus());
    }
}
