package br.com.betohardman.leads.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

    @RestControllerAdvice
public class TratadorDeErros {

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(MethodArgumentNotValidException.class)

    public Map<String, String> tratarValidacao(MethodArgumentNotValidException e) {

        Map<String, String> erros = new HashMap<>();

        for (FieldError erro : e.getBindingResult().getFieldErrors()){
            erros.put(erro.getField(), erro.getDefaultMessage());
        }
    return erros;


    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LeadNaoEncontrado.class)
    public Map<String, String> tratarNaoEncontrado(LeadNaoEncontrado e) {
        return Map.of("erro", "lead nao encontrado");
    }
}
