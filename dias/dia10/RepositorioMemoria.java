import java.util.ArrayList;
import java.util.List; 
  
 public class RepositorioMemoria implements Repositorio {

 private List<Cliente> itens = new ArrayList<>(); 
 
 @Override
 public void salvar(Cliente c) {
  itens.add(c);
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

}
