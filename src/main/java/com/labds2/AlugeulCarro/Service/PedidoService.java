package com.labds2.AlugeulCarro.Service;

import com.labds2.AlugeulCarro.Model.Pedido;
import com.labds2.AlugeulCarro.Repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository repository;

    public Pedido criarPedido(Pedido pedido) {
        pedido.setStatus("PENDENTE");
        return repository.save(pedido);
    }

    public List<Pedido> listarPorCliente(Long clienteId) {
        return repository.findByClienteId(clienteId);
    }
}