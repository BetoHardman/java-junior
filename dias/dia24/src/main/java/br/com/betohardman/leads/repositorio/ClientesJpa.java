package br.com.betohardman.leads.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.betohardman.leads.modelo.Cliente;

// Repare: e uma INTERFACE, e ela esta VAZIA de implementacao.
// Voce nao escreve o codigo destes metodos. O Spring Data le o NOME
// de cada um e escreve o SQL sozinho, em tempo de execucao.
//
//   JpaRepository<Cliente, Long>  ja traz de graca:
//     save, findById, findAll, count, deleteById, e mais uns 15.
public interface ClientesJpa extends JpaRepository<Cliente, Long> {
    List<Cliente> findByStatus(String status);
}
