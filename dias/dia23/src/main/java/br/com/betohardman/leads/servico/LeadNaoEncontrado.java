package br.com.betohardman.leads.servico;

// Uma excecao sua, com nome do seu negocio.
// Mudou de pacote hoje: quem decide que um lead nao existe e a REGRA,
// nao a camada que fala HTTP.
public class LeadNaoEncontrado extends RuntimeException {
}
