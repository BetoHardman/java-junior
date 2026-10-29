import java.util.List;


public interface Repositorio {

 void salvar(Cliente c);
 List<Cliente> porStatus(String status);
 List<Cliente> listar();
 int total();

 }



