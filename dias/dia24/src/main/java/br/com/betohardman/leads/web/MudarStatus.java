package br.com.betohardman.leads.web;

import jakarta.validation.constraints.NotBlank;

public record MudarStatus(@NotBlank(message = "status e obrigatorio") String status) {
}
