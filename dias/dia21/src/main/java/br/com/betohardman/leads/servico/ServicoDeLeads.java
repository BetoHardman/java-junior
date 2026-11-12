package br.com.betohardman.leads.servico;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.betohardman.leads.modelo.Cliente;
import br.com.betohardman.leads.repositorio.Repositorio;

// TODO-1: a anotacao que diz "esta classe guarda a REGRA DE NEGOCIO".
//         E prima do @Component e do @Repository: muda so o papel.
public class ServicoDeLeads {

    private final Repositorio repo;

    // TODO-2: o construtor que pede um Repositorio.
    //         Mesmo desenho do construtor do ControladorLeads de ontem.

    // ---------------------------------------------------------------
    // Este aqui vem pronto, como exemplo do formato dos outros quatro.
    // ---------------------------------------------------------------
    public Map<String, Integer> resumo() {
        return repo.contarPorStatus();
    }

    public List<Cliente> listar(String status) {
        // TODO-3: sem status, devolver repo.listar().
        //         Com status, devolver repo.porStatus(status).
        return null;
    }

    public Cliente buscar(Long id) {
        // TODO-4: procurar com repo.buscarPorId(id).
        //         Veio null? lancar new LeadNaoEncontrado().
        //         Achou? devolver.
        return null;
    }

    public Cliente criar(String nome, String whatsapp) {
        // TODO-5: montar um Cliente novo, salvar no repo e devolver.
        return null;
    }

    public Cliente mudarStatus(Long id, String novoStatus) {
        // TODO-6: pegar o lead (da pra reaproveitar o buscar() daqui de cima,
        //         e assim o 404 sai de graca), mudar o status e devolver.
        return null;
    }
}
