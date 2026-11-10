package br.com.betohardman.leads.web;

// TODO-1: o import do NotBlank
// TODO-3: o import do Pattern

public record NovoLead(

        // TODO-1: a anotacao que exige nome preenchido,
        //         com a mensagem "nome e obrigatorio"
        String nome,

        // TODO-2: a mesma anotacao do TODO-1 aqui,
        //         com a mensagem "whatsapp e obrigatorio"
        // TODO-3: a anotacao que exige exatamente 11 digitos,
        //         com a mensagem "whatsapp precisa ter 11 digitos"
        String whatsapp) {
}
