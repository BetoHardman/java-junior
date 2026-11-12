package br.com.betohardman.leads.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

// @Entity = "esta classe vira uma TABELA no banco".
// @Inheritance = Cliente e ClienteVip dividem a mesma tabela; o Hibernate
// cria sozinho uma coluna DTYPE pra saber quem e quem.
// TODO-1: a anotacao que diz "esta classe vira uma TABELA no banco"
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Cliente {

    // @Id = esta e a chave primaria.
    // @GeneratedValue = quem gera o numero agora e o BANCO, nao o seu codigo.
    // TODO-2: duas anotacoes aqui, uma por linha:
    //   a) a que marca este campo como chave primaria
    //   b) a que manda o BANCO gerar o numero:
    //      @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    protected String nome;
    protected String whatsapp;
    protected String status;
 
    // O Hibernate precisa montar o objeto vazio antes de preencher campo a
    // campo com o que veio do banco. Por isso este construtor sem argumento.
    // protected = ninguem de fora usa, so o framework.
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
