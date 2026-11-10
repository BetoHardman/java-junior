package br.com.betohardman.leads.modelo;

public class ClienteVip extends Cliente {

 private int desconto; 


 public ClienteVip(String nome, String whatsapp, int desconto) {
 super(nome, whatsapp);
 this.desconto = desconto; 
 }

 @Override 
 public double valorHora() {
  return super.valorHora() * (100 - desconto) / 100.0;
 }

 @Override
 public String resumo() {
  return super.resumo() + " [VIP -" + desconto + "%]";
 }

    public int getDesconto() {
        return desconto;
    }



}
