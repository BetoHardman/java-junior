package br.com.betohardman.leads.web;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import jakarta.validation.constraints.NotBlank;
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
    public List<Cliente> listar(@RequestParam(required = false) String status) {
        if (status == null) {
            return repo.listar();
        }
    return repo.porStatus(status);

    }

    @GetMapping("/leads/resumo")
    public Map<String, Integer> resumo() {

        return repo.contarPorStatus();
    }

    @GetMapping("/leads/{id}")
    public Cliente buscar (@PathVariable Long id) {
        Cliente c = repo.buscarPorId(id);
        if (c == null) {
            throw new LeadNaoEncontrado();
        }
        return c;
    }


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
