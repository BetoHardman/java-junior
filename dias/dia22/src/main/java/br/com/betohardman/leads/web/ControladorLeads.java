package br.com.betohardman.leads.web;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.betohardman.leads.modelo.Cliente;
import br.com.betohardman.leads.servico.ServicoDeLeads;

// Repare no tamanho deste arquivo hoje. Cada metodo tem UMA linha.
// O controlador so traduz HTTP: le o endereco, le o corpo, chama o servico.
@RestController
public class ControladorLeads {

    private final ServicoDeLeads servico;

    public ControladorLeads(ServicoDeLeads servico) {
        this.servico = servico;
    }

    @GetMapping("/leads")
    public List<Cliente> listar(@RequestParam(required = false) String status) {
        return servico.listar(status);
    }

    @GetMapping("/leads/resumo")
    public Map<String, Integer> resumo() {
        return servico.resumo();
    }

    @GetMapping("/leads/{id}")
    public Cliente buscar(@PathVariable Long id) {
        return servico.buscar(id);
    }

    @PostMapping("/leads")
    public Cliente criar(@Valid @RequestBody NovoLead novo) {
        return servico.criar(novo.nome(), novo.whatsapp());
    }

    @PutMapping("/leads/{id}")
    public Cliente mudarStatus(@PathVariable Long id, @Valid @RequestBody MudarStatus mudanca) {
        return servico.mudarStatus(id, mudanca.status());
    }
}
