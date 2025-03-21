package com.labds2.AlugeulCarro.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.labds2.AlugeulCarro.Model.Cliente;
import com.labds2.AlugeulCarro.Model.Emprego;
import com.labds2.AlugeulCarro.Repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }


    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }


    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }


    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }


    @Transactional
    public Cliente salvar(Cliente cliente) {
        if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }
        
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
    
        
        return clienteRepository.save(cliente);
    }


    @Transactional
    public Cliente adicionarEmprego(Long clienteId, Emprego emprego) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        
        if (!cliente.adicionarEmprego(emprego)) {
            throw new IllegalStateException("Cliente já possui o número máximo de empregos (3)");
        }
        
        return clienteRepository.save(cliente);
    }


    @Transactional
    public Cliente removerEmprego(Long clienteId, Emprego emprego) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        
        cliente.removerEmprego(emprego);
        
        return clienteRepository.save(cliente);
    }


    @Transactional
    public void excluir(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
        
        clienteRepository.deleteById(id);
    }
    
    public List<Cliente> buscarClientesComRendaAcimaDe(double valorMinimo) {
        return clienteRepository.findClientesComRendaAcimaDe(valorMinimo);
    }
    
    public Optional<Cliente> autenticar(String login, String senha) {
        Optional<Cliente> clienteOpt = clienteRepository.findByLogin(login);
        
        if (clienteOpt.isPresent() && clienteOpt.get().validarSenha(senha)) {
            return clienteOpt;
        }
        
        return Optional.empty();
    }
}