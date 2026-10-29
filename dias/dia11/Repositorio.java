import java.util.List;
import java.util.Map;



public interface Repositorio {

 void salvar(Cliente c);
 List<Cliente> porStatus(String status);
 List<Cliente> listar();
 int total();
 Map<String, Integer> contarPorStatus();

 }



