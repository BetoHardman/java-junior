package br.com.betohardman.leads.repositorio;

import java.util.List;
import java.util.Map;
import br.com.betohardman.leads.modelo.Cliente;


public interface Repositorio {

 void salvar(Cliente c);
 List<Cliente> porStatus(String status);
 List<Cliente> listar();
 int total();
 Map<String, Integer> contarPorStatus();

 }



