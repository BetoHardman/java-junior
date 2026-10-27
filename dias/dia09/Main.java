public class Main {
    public static void main(String[] args) {
        Repositorio repo = new RepositorioMemoria();

        repo.salvar(new Cliente("Luna Toys", "61993687124"));
        repo.salvar(new ClienteVip("Bruna Severo", "61999990000", 20));
        repo.salvar(new Cliente("Creator Pro", "61988887777"));

        // o array de 10 posicoes do dia 8 estouraria na 11a volta daqui
        for (int i = 1; i <= 9; i++) {
            repo.salvar(new Cliente("Cliente " + i, "6190000000" + i));
        }

        System.out.println("total: " + repo.total());
        System.out.println("--- carteira ---");
        for (Cliente c : repo.listar()) {
            System.out.println(c.resumo() + " -> " + c.valorHora());
        }
    }
}
