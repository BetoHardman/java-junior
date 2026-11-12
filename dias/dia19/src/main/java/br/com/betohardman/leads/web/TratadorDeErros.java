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

    // TODO-5: um segundo metodo de plantao, igual ao de cima, mas para a
    //         excecao LeadNaoEncontrado. O codigo e 404 (HttpStatus.NOT_FOUND)
    //         e o corpo e Map.of("erro", "lead nao encontrado").
}
