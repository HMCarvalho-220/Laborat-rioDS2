package com.labds2.AlugeulCarro.Service;

import com.labds2.AlugeulCarro.Model.Cliente;
import com.labds2.AlugeulCarro.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setRg(clienteAtualizado.getRg());
                    cliente.setCpf(clienteAtualizado.getCpf());
                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setEndereco(clienteAtualizado.getEndereco());
                    cliente.setProfissao(clienteAtualizado.getProfissao());
                    cliente.setLogin(clienteAtualizado.getLogin());
                    cliente.setSenha(clienteAtualizado.getSenha());
                    return clienteRepository.save(cliente);
                })
                .orElseGet(() -> {
                    clienteAtualizado.setId(id);
                    return clienteRepository.save(clienteAtualizado);
                });
    }
}