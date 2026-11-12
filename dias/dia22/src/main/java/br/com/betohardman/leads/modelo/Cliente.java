package br.com.betohardman.leads.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;


    @Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    protected String nome;
    protected String whatsapp;
    protected String status;
 

    protected Cliente() {
    }

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


    // quem preenche o id e o repositorio, na hora de salvar
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

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
