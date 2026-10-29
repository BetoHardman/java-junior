import java.util.List;

public class Main {
    public static void main(String[] args) {
        Repositorio repo = new RepositorioMemoria();

        Cliente luna = new Cliente("Luna Toys", "61993687124");
        Cliente bruna = new ClienteVip("Bruna Severo", "61999990000", 20);
        Cliente creator = new Cliente("Creator Pro", "61988887777");
        repo.salvar(luna);
        repo.salvar(bruna);
        repo.salvar(creator);

        luna.mudarStatus("fechado");
        bruna.mudarStatus("contato");
        creator.mudarStatus("perdido");   // nao existe: tem que ser recusado

        String[] etapas = {"novo", "contato", "fechado"};
        for (String etapa : etapas) {
            List<Cliente> lista = repo.porStatus(etapa);
            System.out.println(etapa + ": " + lista.size());
            for (Cliente c : lista) {
                System.out.println("  " + c.resumo());
            }
        }

        // texto que chega de formulario nao e o mesmo objeto que o "fechado" do codigo
        String digitado = new String("fechado");
        System.out.println("--- armadilha ---");
        System.out.println("== : " + (digitado == "fechado"));
        System.out.println("equals: " + digitado.equals("fechado"));
    }
}
