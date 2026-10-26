public class Main {
    public static void main(String[] args) {
        // repare: a VARIAVEL e do tipo Repositorio (a interface),
        // o OBJETO e um RepositorioMemoria (a implementacao).
        Repositorio repo = new RepositorioMemoria();

        repo.salvar(new Cliente("Luna Toys", "61993687124"));
        repo.salvar(new ClienteVip("Bruna Severo", "61999990000", 20));
        repo.salvar(new Cliente("Creator Pro", "61988887777"));

        System.out.println("total: " + repo.total());
        System.out.println("--- carteira ---");
        for (Cliente c : repo.listar()) {
            System.out.println(c.resumo() + " -> " + c.valorHora());
        }
    }
}
