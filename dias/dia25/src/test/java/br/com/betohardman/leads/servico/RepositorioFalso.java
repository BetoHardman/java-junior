package br.com.betohardman.leads.servico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.betohardman.leads.modelo.Cliente;
import br.com.betohardman.leads.repositorio.Repositorio;

// ===============================================================
// O DUBLE. Vem pronto — voce nao mexe aqui.
//
// E um Repositorio de verdade aos olhos do ServicoDeLeads: implementa
// a MESMA interface do dia 21. Por dentro nao tem banco, nao tem Spring,
// nao tem rede. So um HashMap na memoria.
//
// E por isso que a interface do dia 21 existia. Ela nao serviu so pra
// trocar ArrayList por H2 no dia 22 — ela serve pra voce enfiar um
// repositorio de mentira dentro do servico e testar a REGRA sozinha.
// ===============================================================
public class RepositorioFalso implements Repositorio {

    private final Map<Long, Cliente> dados = new LinkedHashMap<>();
    private Long proximoId = 1L;

    @Override
    public void salvar(Cliente c) {
        if (c.getId() == null) {
            c.setId(proximoId++);
        }
        dados.put(c.getId(), c);
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return dados.get(id);
    }

    @Override
    public void remover(Long id) {
        dados.remove(id);
    }

    @Override
    public List<Cliente> porStatus(String status) {
        List<Cliente> achados = new ArrayList<>();
        for (Cliente c : dados.values()) {
            if (c.getStatus().equals(status)) {
                achados.add(c);
            }
        }
        return achados;
    }

    @Override
    public List<Cliente> listar() {
        return new ArrayList<>(dados.values());
    }

    @Override
    public int total() {
        return dados.size();
    }

    @Override
    public Map<String, Integer> contarPorStatus() {
        Map<String, Integer> contagem = new HashMap<>();
        for (Cliente c : dados.values()) {
            contagem.merge(c.getStatus(), 1, Integer::sum);
        }
        return contagem;
    }
}
