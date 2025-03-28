package com.labds2.AlugeulCarro.Controllers;

import com.labds2.AlugeulCarro.Model.Cliente;
import com.labds2.AlugeulCarro.Service.ClienteService;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.salvar(cliente));
    }

    @GetMapping
    @Transactional
    public ResponseEntity<List<Cliente>> listarTodos() {
        List<Cliente> clientes = clienteService.listarTodos();
        clientes.forEach(c -> Hibernate.initialize(c.getPedidos()));
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        Hibernate.initialize(cliente.getPedidos());
        return ResponseEntity.ok(cliente);
    }
    
    @GetMapping("/cpf/{cpf}")
    @Transactional(readOnly = true)
    public ResponseEntity<Cliente> buscarPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(clienteService.buscarPorCpf(cpf));
    }

    @GetMapping("/buscar")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(clienteService.buscarPorNome(nome));
    }

    @GetMapping("/com-mais-pedidos/{minPedidos}")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Cliente>> getClientesComMaisDeXPedidos(
            @PathVariable int minPedidos) {
        return ResponseEntity.ok(
            clienteService.findClientesComMaisDeXPedidos(minPedidos)
        );
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        clienteService.remover(id);
        return ResponseEntity.noContent().build();
    }
}