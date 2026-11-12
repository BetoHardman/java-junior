package br.com.betohardman.leads.servico;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import br.com.betohardman.leads.modelo.Cliente;
import br.com.betohardman.leads.repositorio.Repositorio;

 @Service
public class ServicoDeLeads {

    private final Repositorio repo;

    public ServicoDeLeads(Repositorio repo) {
        this.repo = repo;
    }

    public Map<String, Integer> resumo() {
        return repo.contarPorStatus();
    }

    public List<Cliente> listar(String status) {
        if (status == null) {
            return repo.listar();
        }
        return repo.porStatus(status);
    }

    public Cliente buscar(Long id) {
    Cliente c = repo.buscarPorId(id);
    if (c == null) {
        throw new LeadNaoEncontrado();
    }
        return c;
    }

    public Cliente criar(String nome, String whatsapp) {
        Cliente c = new Cliente(nome, whatsapp);
        repo.salvar(c);
        return c;
    }

    public Cliente mudarStatus(Long id, String novoStatus) {
        Cliente c = buscar(id);
        c.mudarStatus(novoStatus);
        // TODO-6: uma linha aqui. Com banco, mudar o objeto nao basta.
        return c;
        }

    }

