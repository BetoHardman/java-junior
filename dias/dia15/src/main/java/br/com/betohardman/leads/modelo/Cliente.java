package br.com.betohardman.leads.modelo;

public class Cliente {
    protected String nome;
    protected String whatsapp;
    protected String status;
 
    public Cliente(String nome, String whatsapp) {
        this.nome = nome;
        this.whatsapp = whatsapp;
        this.status = "novo";
    }

    public double valorHora() {
        return 120.0;
    }


 public String resumo() {
 return nome + " (" + whatsapp + ") - " + status;
 } 


    // TODO-4: o getter do nome
    // TODO-5: o getter do whatsapp

    public String getStatus() {
 return status;
 }
    public void mudarStatus(String novo) {
 if (novo.equals("novo") ||
 novo.equals("contato") ||
 novo.equals("fechado")) {

 this.status = novo;


 } else {
   System.out.println("status invalido: " + novo);
  } 
 }
}
