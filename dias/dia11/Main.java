import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Repositorio repo = new RepositorioMemoria();

        Cliente luna    = new Cliente("Luna Toys", "61993687124");
        Cliente bruna   = new ClienteVip("Bruna Severo", "61999990000", 20);
        Cliente creator = new Cliente("Creator Pro", "61988887777");
        Cliente lu      = new Cliente("Dona Lu Doces", "61977776666");
        Cliente studio  = new ClienteVip("Studio Alon", "61966665555", 10);

        repo.salvar(luna);
        repo.salvar(bruna);
        repo.salvar(creator);
        repo.salvar(lu);
        repo.salvar(studio);

        luna.mudarStatus("fechado");
        bruna.mudarStatus("contato");
        creator.mudarStatus("contato");
        lu.mudarStatus("fechado");
        // o studio continua "novo"

        Map<String, Integer> resumo = repo.contarPorStatus();

        System.out.println("--- resumo da carteira ---");
        String[] etapas = {"novo", "contato", "fechado"};
        for (String etapa : etapas) {
            System.out.println(etapa + ": " + resumo.get(etapa));
        }

        // nenhum cliente esta "perdido": a chave nao existe no mapa
        System.out.println("--- armadilha ---");
        System.out.println("perdido com get: " + resumo.get("perdido"));
        System.out.println("perdido com getOrDefault: " + resumo.getOrDefault("perdido", 0));

        int soma = 0;
        for (int n : resumo.values()) {
            soma = soma + n;
        }
        System.out.println("soma do resumo: " + soma);
        System.out.println("total do repositorio: " + repo.total());
    }
}
