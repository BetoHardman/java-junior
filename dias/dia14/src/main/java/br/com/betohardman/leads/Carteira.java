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
