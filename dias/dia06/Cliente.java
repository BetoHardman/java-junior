public class Cliente {

    
 private String nome;;
 private String telefone; 
 private String status;
 

 public Cliente(String nome, String telefone) {
 this.nome = nome;
 this.telefone = telefone;
 this.status = "novo";
  }

    public String getNome() {
 return nome;
 }



 public String getWhatsapp() {
 return telefone;
 }    


  public String getStatus() {
 return status;
 }

  public boolean setStatus(String novo) {
if (novo.equals("novo") || novo.equals("conversando")
     || novo.equals("fechado") || novo.equals("perdido")) {
this.status = novo;
 return true;  
 }
 return false;
}



public String resumo() {
 return nome + " (" + telefone + ") - " + status;
 }
}



