package br.com.betohardman.leads.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
// TODO-2: o import do PostMapping
// TODO-3: o import do RequestBody
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
    public List<Cliente> listar() {
        return repo.listar();
    }

    // TODO-4: a anotacao que atende POST no endereco /leads
    public Cliente criar(/* TODO-5: o parametro que recebe o corpo do pedido */) {
        // TODO-6: montar o Cliente com os dados do NovoLead,
        //         salvar no repositorio e devolver ele.
        return null;
    }
}
