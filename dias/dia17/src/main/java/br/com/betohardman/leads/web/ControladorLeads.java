package br.com.betohardman.leads.web;

import java.util.List;

// TODO-4: o import do Valid

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public List<Cliente> listar() {
        return repo.listar();
    }

    @PostMapping("/leads")
        public Cliente criar(/* TODO-4: a anotacao que manda conferir */ @RequestBody NovoLead novo) {
           Cliente c = new Cliente(novo.nome(), novo.whatsapp());
           repo.salvar(c);
           return c;

        }
}
