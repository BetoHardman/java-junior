package br.com.betohardman.leads.servico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.betohardman.leads.modelo.Cliente;

// Repare no que NAO tem aqui: nenhuma anotacao do Spring.
// Esta classe nao sobe aplicacao, nao abre porta, nao toca banco.
// Ela testa a REGRA, sozinha, em milissegundos.
class ServicoDeLeadsTest {

    private ServicoDeLeads servico;

    @BeforeEach
    void antes_de_cada_teste() {
        servico = new ServicoDeLeads(new RepositorioFalso());
    }

    // ===============================================================
    // ESTE VEM PRONTO — e o molde dos outros tres.
    // ===============================================================
    @Test
    void criar_devolve_lead_com_id_e_status_novo() {
        Cliente c = servico.criar("Luna Toys", "61900001111");

        assertNotNull(c.getId(), "o repositorio devia ter preenchido o id");
        assertEquals("Luna Toys", c.getNome());
        assertEquals("novo", c.getStatus(), "lead recem-criado nasce com status novo");
    }

    @Test
    void buscar_id_inexistente_lanca_LeadNaoEncontrado() {
        assertThrows(LeadNaoEncontrado.class, () -> servico.buscar(999L));
    }

    @Test
    void listar_sem_filtro_devolve_todos_os_criados() {
        servico.criar("Luna Toys", "61900001111");
        servico.criar("Studio Alon", "61900002222");

        assertEquals(2, servico.listar(null).size());
    }

    @Test
    void mudar_status_para_valor_invalido_mantem_o_status_anterior() {
        Cliente c = servico.criar("Luna Toys", "61900001111");

        servico.mudarStatus(c.getId(), "cafe com leite");

        assertEquals("novo", servico.buscar(c.getId()).getStatus(),
                "status invalido nao pode entrar");
    }
}
