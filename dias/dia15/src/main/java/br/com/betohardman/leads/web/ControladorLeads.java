package br.com.betohardman.leads.web;

import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import br.com.betohardman.leads.modelo.Cliente;
import br.com.betohardman.leads.repositorio.Repositorio;
import org.springframework.web.bind.annotation.RestController;

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
}
