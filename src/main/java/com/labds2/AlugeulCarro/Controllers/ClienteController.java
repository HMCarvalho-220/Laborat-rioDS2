package com.labds2.AlugeulCarro.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.labds2.AlugeulCarro.Model.Cliente;
import com.labds2.AlugeulCarro.Model.Emprego;
import com.labds2.AlugeulCarro.Services.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cliente não encontrado com ID: " + id));
    }


    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Cliente> buscarPorCpf(@PathVariable String cpf) {
        return clienteService.buscarPorCpf(cpf)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cliente não encontrado com CPF: " + cpf));
    }


    @GetMapping("/nome")
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam String nome) {
        List<Cliente> clientes = clienteService.buscarPorNome(nome);
        return ResponseEntity.ok(clientes);
    }


    @GetMapping("/renda")
    public ResponseEntity<List<Cliente>> buscarPorRendaMinima(@RequestParam double valorMinimo) {
        List<Cliente> clientes = clienteService.buscarClientesComRendaAcimaDe(valorMinimo);
        return ResponseEntity.ok(clientes);
    }

    
    @PostMapping
    public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente) {
        try {
            Cliente novoCliente = clienteService.salvar(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteService.buscarPorId(id)
                .map(clienteExistente -> {
                    cliente.setCpf(id);
                    try {
                        Cliente clienteAtualizado = clienteService.salvar(cliente);
                        return ResponseEntity.ok(clienteAtualizado);
                    } catch (IllegalArgumentException e) {
                        throw new ResponseStatusException(
                                HttpStatus.BAD_REQUEST, e.getMessage());
                    }
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cliente não encontrado com ID: " + id));
    }


    @PostMapping("/{id}/empregos")
    public ResponseEntity<Cliente> adicionarEmprego(@PathVariable Long id, @RequestBody Emprego emprego) {
        try {
            Cliente clienteAtualizado = clienteService.adicionarEmprego(id, emprego);
            return ResponseEntity.ok(clienteAtualizado);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @DeleteMapping("/{id}/empregos/{empregoId}")
    public ResponseEntity<Cliente> removerEmprego(@PathVariable Long id, @PathVariable Long empregoId) {
        try {
            Cliente cliente = clienteService.buscarPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));
            
            Emprego emprego = cliente.getEmpregos().stream()
                    .filter(e -> e.getId().equals(empregoId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Emprego não encontrado com ID: " + empregoId));
            
            Cliente clienteAtualizado = clienteService.removerEmprego(id, emprego);
            return ResponseEntity.ok(clienteAtualizado);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            clienteService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao excluir cliente: " + e.getMessage());
        }
    }

    @PostMapping("/autenticar")
    public ResponseEntity<Cliente> autenticar(@RequestBody CredenciaisDTO credenciais) {
        return clienteService.autenticar(credenciais.getLogin(), credenciais.getSenha())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Credenciais inválidas"));
    }
    

    public static class CredenciaisDTO {
        private String login;
        private String senha;
        
        public String getLogin() {
            return login;
        }
        
        public void setLogin(String login) {
            this.login = login;
        }
        
        public String getSenha() {
            return senha;
        }
        
        public void setSenha(String senha) {
            this.senha = senha;
        }
    }
}
