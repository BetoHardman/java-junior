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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControladorLeadsTest {

    @LocalServerPort
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

    @Test
    void listar_devolve_os_cinco_leads_da_carteira() throws Exception {
        HttpResponse<String> r = get("/leads");

        assertEquals(200, r.statusCode());
        assertTrue(r.body().contains("Luna Toys"), "esperava a Luna Toys na lista");
        assertTrue(r.body().contains("Studio Alon"), "esperava o Studio Alon na lista");
    }

    @Test
    void buscar_id_inexistente_devolve_404() throws Exception {
        HttpResponse<String> r = get("/leads/999");

        assertEquals(404, r.statusCode());
        assertTrue(r.body().contains("lead nao encontrado"), "esperava a mensagem de nao encontrado");
    }

    @Test
    void criar_lead_valido_devolve_200() throws Exception {
        HttpResponse<String> r = post("/leads", "{\"nome\":\"Alon Warm\",\"whatsapp\":\"61900001111\"}");

        assertEquals(200, r.statusCode());
        assertTrue(r.body().contains("Alon Warm"), "esperava o lead criado na resposta");
    }

    @Test
    void criar_lead_sem_whatsapp_devolve_400() throws Exception {
        HttpResponse<String> r = post("/leads", "{\"nome\":\"Faltando Campo\"}");

        assertEquals(400, r.statusCode());
        assertTrue(r.body().contains("whatsapp e obrigatorio"), "esperava a mensagem de validacao");
    }
}
