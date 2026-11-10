package br.com.betohardman.leads.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record NovoLead(
        @NotBlank (message = "nome e obrigatorio")
        String nome,

        @NotBlank(message = "whatsapp e obrigatorio")
                @Pattern(regexp = "[0-9]{11}", message = "whatsapp precisa ter 11 digitos")
        String whatsapp) {
}
