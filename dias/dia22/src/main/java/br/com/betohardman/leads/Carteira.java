package br.com.betohardman.leads;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.betohardman.leads.modelo.Cliente;
import br.com.betohardman.leads.modelo.ClienteVip;
import br.com.betohardman.leads.repositorio.Repositorio;

// @Component = "Spring, essa classe e sua. Cria ela."
@Component
public class Carteira implements CommandLineRunner {

    private final Repositorio repo;

    // Repare: nenhum "new RepositorioMemoria()" aqui.
    // O Spring ve que a Carteira precisa de um Repositorio
    // e entrega um pronto neste construtor.
    public Carteira(Repositorio repo) {
        this.repo = repo;
    }

    // o Spring chama este metodo sozinho, depois de subir
    @Override
    public void run(String... args) {
        Cliente luna    = new Cliente("Luna Toys", "61993687124");
        Cliente bruna   = new ClienteVip("Bruna Severo", "61999990000", 20);
        Cliente creator = new Cliente("Creator Pro", "61988887777");
        Cliente lu      = new Cliente("Dona Lu Doces", "61977776666");
        Cliente studio  = new ClienteVip("Studio Alon", "61966665555", 10);

        // ATENCAO — esta ordem MUDOU no dia 22, e o motivo importa.
        // Antes era salvar primeiro e mudar o status depois: funcionava
        // porque o ArrayList guardava o MESMO objeto que estava aqui.
        // Com banco nao funciona mais. Depois do salvar, o objeto que voce
        // tem na mao e so uma copia; mexer nele nao mexe na linha da tabela.
        // Entao: monta o objeto inteiro primeiro, salva depois.
        luna.mudarStatus("fechado");
        bruna.mudarStatus("contato");
        creator.mudarStatus("contato");
        lu.mudarStatus("fechado");

        repo.salvar(luna);
        repo.salvar(bruna);
        repo.salvar(creator);
        repo.salvar(lu);
        repo.salvar(studio);

        Map<String, Integer> resumo = repo.contarPorStatus();

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

        // Novidade: a partir de hoje o programa NAO termina — o servidor
        // fica no ar. Quando a saida vai pra um arquivo em vez da tela,
        // o Java segura o texto num buffer e so solta no fim. Como nao ha
        // mais um "fim", a gente manda soltar agora.
        System.out.flush();
    }
}
