package br.com.betohardman.leads.repositorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import br.com.betohardman.leads.modelo.Cliente;

// A PECA-CHAVE DO DIA.
// Esta classe implementa o MESMO Repositorio de sempre — a interface nao
// mudou uma letra. Por dentro, em vez de um ArrayList, ela fala com o banco.
// O ServicoDeLeads nao sabe de nada disso.
@Repository
public class RepositorioBanco implements Repositorio {

    private final ClientesJpa jpa;

    public RepositorioBanco(ClientesJpa jpa) {
        this.jpa = jpa;
    }

    @Override
    public void salvar(Cliente c) {
        // TODO-5a: mandar o jpa guardar
    }

    @Override
    public Cliente buscarPorId(Long id) {
        // findById devolve um Optional: uma caixinha que pode estar vazia.
        // orElse(null) = "me da o que tem dentro, ou null se estiver vazia".
        return jpa.findById(id).orElse(null);
    }

    @Override
    public List<Cliente> listar() {
        return jpa.findAll();
    }

    @Override
    public List<Cliente> porStatus(String status) {
        // TODO-5b: chamar o metodo que voce declarou no TODO-4
        return null;
    }

    @Override
    public int total() {
        return (int) jpa.count();
    }

    @Override
    public Map<String, Integer> contarPorStatus() {
        Map<String, Integer> contagem = new HashMap<>();
        for (Cliente c : jpa.findAll()) {
            String chave = c.getStatus();
            contagem.put(chave, contagem.getOrDefault(chave, 0) + 1);
        }
        return contagem;
    }
}
