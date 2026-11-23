package br.com.betohardman.leads.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

// TODO-1: a anotacao que sobe a aplicacao inteira antes dos testes.
//         Ela recebe um ajuste, pra subir numa porta sorteada:
//         (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControladorLeadsTest {

    // TODO-2: a anotacao que pede ao Spring a porta que ele sorteou.
    //         Vai colada em cima do campo abaixo.
    int porta;

    // ---------------------------------------------------------------
    // Encanamento pronto: duas ajudantes que fazem o pedido HTTP.
    // Nada de Spring aqui — e o cliente HTTP do proprio Java.
    // ---------------------------------------------------------------
    private HttpResponse<String> get(String caminho) throws Exception {
        HttpRequest pedido = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + porta + caminho))
                .GET()
                .build();
        return HttpClient.newHttpClient().send(pedido, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> post(String caminho, String corpoJson) throws Exception {
        HttpRequest pedido = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + porta + caminho))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(corpoJson))
                .build();
        return HttpClient.newHttpClient().send(pedido, HttpResponse.BodyHandlers.ofString());
    }

    // ===============================================================
    // ESTE VEM PRONTO — e o molde dos outros tres.
    // ===============================================================
    @Test
    void listar_devolve_os_cinco_leads_da_carteira() throws Exception {
        HttpResponse<String> r = get("/leads");

        assertEquals(200, r.statusCode());
        assertTrue(r.body().contains("Luna Toys"), "esperava a Luna Toys na lista");
        assertTrue(r.body().contains("Studio Alon"), "esperava o Studio Alon na lista");
    }

    // TODO-3: um teste que chama get("/leads/999") e confere:
    //         - que o codigo e 404
    //         - que o corpo contem "lead nao encontrado"

    // TODO-4: um teste que chama post("/leads", ...) com um lead VALIDO
    //         e confere:
    //         - que o codigo e 200
    //         - que o corpo contem "Alon Warm"
    //         O corpo do pedido, pra copiar:
    //         "{\"nome\":\"Alon Warm\",\"whatsapp\":\"61900001111\"}"

    // TODO-5: um teste que chama post("/leads", ...) SEM o whatsapp
    //         e confere:
    //         - que o codigo e 400
    //         - que o corpo contem "whatsapp e obrigatorio"
    //         O corpo do pedido, pra copiar:
    //         "{\"nome\":\"Faltando Campo\"}"
}
