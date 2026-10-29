import java.util.Map;
import org.apache.commons.lang3.StringUtils;

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

        Map<String, Integer> resumo = repo.contarPorStatus();

        // StringUtils vem da biblioteca commons-lang3, declarada no pom.xml
        System.out.println(StringUtils.center(" CARTEIRA ", 30, "="));

        String[] etapas = {"novo", "contato", "fechado"};
        for (String etapa : etapas) {
            System.out.println(StringUtils.rightPad(etapa, 12, '.') + resumo.getOrDefault(etapa, 0));
        }

        System.out.println(StringUtils.repeat("-", 30));
        for (Cliente c : repo.listar()) {
            System.out.println(c.resumo());
        }
        System.out.println(StringUtils.repeat("=", 30));
        System.out.println("total: " + repo.total());
    }
}
