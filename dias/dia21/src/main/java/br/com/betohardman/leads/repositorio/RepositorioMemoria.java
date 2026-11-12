package br.com.betohardman.leads.repositorio;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import br.com.betohardman.leads.modelo.Cliente;
  
 @Repository
 public class RepositorioMemoria implements Repositorio {

 private List<Cliente> itens = new ArrayList<>();

 // o proximo id a entregar. 1, 2, 3... nunca repete.
 private long proximoId = 1;
 
 @Override
 public void salvar(Cliente c) {
  c.setId(proximoId);
  proximoId = proximoId + 1;
  itens.add(c);
 }

 @Override
 public Cliente buscarPorId(Long id) {
  for (Cliente c : itens) {
    if (id.equals(c.getId())) {
      return c;
    }
  }
  return null;
 }

 @Override
 public List<Cliente> listar() {
  return itens;
 }

 @Override
 public int total() { 
  return itens.size();
 }

 @Override
 public List<Cliente> porStatus(String status) {
 List<Cliente> achados = new ArrayList<>();
 
 for (Cliente c : itens) {
   if (c.getStatus().equals(status)) {
     achados.add(c);
 }
}

    return achados;
 }

 @Override
  public Map<String, Integer> contarPorStatus() {
  Map<String, Integer> contagem = new HashMap<>();

  for (Cliente c : itens) {
    String chave = c.getStatus();
    contagem.put(chave, contagem.getOrDefault(chave, 0) + 1);


  }
  return contagem;
 }



}
