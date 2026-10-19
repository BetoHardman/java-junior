public class Cliente {

String nome;
String telefone;
String status;    


public Cliente (String nome, String telefone) {
this.nome = nome;
this.telefone = telefone;
this.status = "novo"; 
}    


  void mudarStatus(String novo) {
this.status = novo;  
 }



   String resumo() {
return nome + " (" + telefone + ") - " + status; 

 }
}
