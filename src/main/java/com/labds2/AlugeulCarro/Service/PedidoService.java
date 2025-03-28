package com.labds2.AlugeulCarro.Service;

import com.labds2.AlugeulCarro.Model.*;
import com.labds2.AlugeulCarro.Repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final AutomovelService automovelService;
    private final ClienteService clienteService;

    public PedidoService(PedidoRepository pedidoRepository,
                        AutomovelService automovelService,
                        ClienteService clienteService) {
        this.pedidoRepository = pedidoRepository;
        this.automovelService = automovelService;
        this.clienteService = clienteService;
    }

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        // Garante que as entidades relacionadas estão carregadas
        pedido.setCliente(clienteService.buscarPorId(pedido.getCliente().getId()));
        pedido.setAutomovel(automovelService.buscarPorId(pedido.getAutomovel().getId()));
        
        validarPedido(pedido);
        calcularValorTotal(pedido);
        pedido.setStatus(StatusPedido.PENDENTE);
        
        return pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }

    @Transactional
    public Pedido atualizarStatus(Long id, StatusPedido status) {
        Pedido pedido = buscarPorId(id);
        
        if (pedido.getStatus() == StatusPedido.CANCELADO || 
            pedido.getStatus() == StatusPedido.FINALIZADO) {
            throw new IllegalStateException("Pedido não pode ter status alterado");
        }
        
        pedido.setStatus(status);
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelarPedido(Long id) {
        Pedido pedido = buscarPorId(id);
        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public boolean isAutomovelDisponivel(Long automovelId, LocalDate inicio, LocalDate fim) {
        return pedidoRepository.findPedidosSobrepostos(
            automovelId, 
            StatusPedido.CONFIRMADO,
            inicio, 
            fim
        ).isEmpty();
    }

    private void validarPedido(Pedido pedido) {
        if (pedido.getDataInicio() == null || pedido.getDataFim() == null) {
            throw new IllegalArgumentException("Datas são obrigatórias");
        }
        if (pedido.getDataInicio().isAfter(pedido.getDataFim())) {
            throw new IllegalArgumentException("Data inicial deve ser anterior à final");
        }
        if (pedido.getDataInicio().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data deve ser futura");
        }
        if (!isAutomovelDisponivel(pedido.getAutomovel().getId(), pedido.getDataInicio(), pedido.getDataFim())) {
            throw new IllegalStateException("Automóvel indisponível no período");
        }
    }

    private void calcularValorTotal(Pedido pedido) {
        long dias = ChronoUnit.DAYS.between(pedido.getDataInicio(), pedido.getDataFim());
        pedido.setValorTotal(dias * pedido.getAutomovel().getPrecoDiaria());
    }
}