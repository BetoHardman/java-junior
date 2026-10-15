public class Main {
    public static void main(String[] args) {
        // 1. for + if/else
        for (int i = 1; i <= 5; i++) {
            if (i % 2 == 0) {
                System.out.println(i + " par");
            } else {
                System.out.println(i + " impar");
            }
        }

        // 2. switch
        String status = "fechado";
        switch (status) {
            case "novo" -> System.out.println("Responder hoje");
            case "conversando" -> System.out.println("Fazer followup");
            case "fechado" -> System.out.println("Comecar o site");
            default -> System.out.println("Status desconhecido");
        }

        // 3. ternario
        int leads = 7;
        String semana = leads >= 5 ? "boa" : "fraca";
        System.out.println("Semana " + semana);

        // 4. for-each
        String[] sites = {"lunatoys", "creator-pro", "metodo-produza"};
        for (String s : sites) {
            System.out.println("no ar: " + s);
        }

        // 5. while
        int contador = 3;
        while (contador > 0) {
            System.out.println("faltam " + contador);
            contador--;
        }
    }
}
