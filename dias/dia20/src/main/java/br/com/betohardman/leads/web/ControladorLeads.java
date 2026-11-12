package br.com.betohardman.leads.web;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.betohardman.leads.modelo.Cliente;
import br.com.betohardman.leads.repositorio.Repositorio;

@RestController
public class ControladorLeads {

    private final Repositorio repo;

    public ControladorLeads(Repositorio repo) {
        this.repo = repo;
    }

    @GetMapping("/leads")
    public List<Cliente> listar(/* TODO-1: o parametro opcional ?status= */) {

        // TODO-2: se nao veio status, devolver a lista inteira (repo.listar()).
        //         Se veio, devolver so os daquele status (repo.porStatus(...)).

        return repo.listar();
    }

    // TODO-3: a anotacao que atende GET no endereco /leads/resumo
    public Map<String, Integer> resumo() {
        // TODO-4: devolver a contagem por status que o repositorio ja sabe fazer
        return null;
    }

    // TODO-5: o metodo inteiro que atende GET /leads/{id} e devolve UM lead.
    //         E o mesmo desenho do PUT la embaixo, sem a parte de mudar:
    //         anotacao, @PathVariable, procurar, 404 se nao achar, devolver.


    @PostMapping("/leads")
        public Cliente criar(@Valid @RequestBody NovoLead novo) {
           Cliente c = new Cliente(novo.nome(), novo.whatsapp());
           repo.salvar(c);
           return c;

        }

    @PutMapping("/leads/{id}")
    public Cliente mudarStatus(@PathVariable Long id,
                               @Valid @RequestBody MudarStatus mudanca) {
        Cliente c = repo.buscarPorId(id);
        if (c == null) {
            throw new LeadNaoEncontrado();
        }
        c.mudarStatus(mudanca.status());
        return c;
    }
}
