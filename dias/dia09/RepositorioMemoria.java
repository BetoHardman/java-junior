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
}
