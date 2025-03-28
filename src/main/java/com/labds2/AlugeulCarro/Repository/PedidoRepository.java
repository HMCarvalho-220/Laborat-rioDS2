package com.labds2.AlugeulCarro.Repository;

import com.labds2.AlugeulCarro.Model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId); // Busca pedidos por cliente
}