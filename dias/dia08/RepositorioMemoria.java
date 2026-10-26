public class RepositorioMemoria implements Repositorio {

 private Cliente[] itens = new Cliente[10];
 private int qtd = 0;

 @Override
 public void salvar(Cliente c) {
 itens[qtd] = c;
 qtd++;
 }

 @Override
 public Cliente[] listar() {
 Cliente[] copia = new Cliente[qtd];
 for (int i = 0; i < qtd; i++) {
  copia[i] = itens[i];
 }
  return copia;
}

 @Override 
 public int total() {
  return qtd;
 }



 }
