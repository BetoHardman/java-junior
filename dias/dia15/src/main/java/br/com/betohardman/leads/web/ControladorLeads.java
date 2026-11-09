package br.com.betohardman.leads.web;

import java.util.List;

// TODO-1: o import do RestController
// TODO-2: o import do GetMapping

import br.com.betohardman.leads.modelo.Cliente;
import br.com.betohardman.leads.repositorio.Repositorio;

// TODO-1: a anotacao que diz "esta classe atende pedidos HTTP"
public class ControladorLeads {

    private final Repositorio repo;

    // TODO-3: o construtor que pede um Repositorio.
    //         E a mesma ideia da Carteira — so muda o nome da classe.

    // TODO-2: a anotacao que amarra este metodo ao endereco /leads
    public List<Cliente> listar() {
        return repo.listar();
    }
}
