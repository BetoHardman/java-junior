package br.com.betohardman.leads.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// TODO-1: a anotacao que diz "esta classe cuida dos erros de TODOS os controladores"
public class TratadorDeErros {

    // TODO-2: a anotacao que define o codigo HTTP da resposta: 400, BAD_REQUEST
    // TODO-3: a anotacao que diz QUAL excecao este metodo atende
    public Map<String, String> tratarValidacao(MethodArgumentNotValidException e) {

        // TODO-4: criar um Map<String, String> vazio chamado erros

        // TODO-5: o laco que percorre e.getBindingResult().getFieldErrors().
        //         Para cada erro, poe no mapa: a chave e o campo,
        //         o valor e a mensagem.

        // TODO-6: devolver o mapa, no lugar deste null
        return null;
    }
}
